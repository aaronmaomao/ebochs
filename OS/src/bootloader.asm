; TAB=4
BOTPAK	EQU		0x00280000		; bootpackのロード先 -> Bootpack加载目标
DSKCAC	EQU		0x00100000		; ディスクキャッシュの場所 -> 磁盘缓存位置
DSKCAC0	EQU		0x00008000		; ディスクキャッシュの場所（リアルモード）->磁盘缓存位置（实模式）

;有关bootinfo的设置
CYLS	equ	0x0ff0
LEDS	equ	0x0ff1
VMODE	equ	0x0ff2	;保存颜色数目的信息。颜色的位数
SCRNX	equ	0x0ff4	;分辨率的x
SCRNY	equ	0x0ff6	;分辨率的y
VRAM	equ	0x0ff8	;图像缓冲区的起始地址，即显存的起始地址

org	0xc200

;设置显卡
mov	bx,	0x4101	;VGA图形模式，320x200x8位彩色模式，调色板模式
mov	ax,	0x4f02	;设置显卡模式
int	0x10
mov	byte[VMODE],	8
mov	word[SCRNX],	640
mov	word[SCRNY],	480
mov	dword[VRAM],	0xe0000000	;显存的起始地址

;用bios取的键盘上各种led指示灯的状态
mov	ah,	0x02
int	0x16
mov	[LEDS],	al

; PICが一切の割り込みを受け付けないようにする
;	AT互換機の仕様では、PICの初期化をするなら、
;	こいつをCLI前にやっておかないと、たまにハングアップする
;	PICの初期化はあとでやる

		MOV		AL,0xff
		OUT		0x21,AL	;屏蔽PIC1
		NOP						; OUT命令を連続させるとうまくいかない機種があるらしいので
		OUT		0xa1,AL	;屏蔽PIC2

		CLI						; 禁止内部中断

; CPUから1MB以上のメモリにアクセスできるように、A20GATEを設定
;为了让CPU能够访问1M以上的内存空间，设定A20GATE

		CALL	waitkbdout
		MOV		AL,0xd1
		OUT		0x64,AL
		CALL	waitkbdout
		MOV		AL,0xdf			; enable A20
		OUT		0x60,AL
		CALL	waitkbdout

; プロテクトモード移行
;开始切换保护模式

[INSTRSET "i486p"]				; 486の命令まで使いたいという記述

		LGDT	[GDTR0]			; 暫定GDTを設定
		MOV		EAX,CR0
		AND		EAX,0x7fffffff	; bit31を0にする（ページング禁止のため）
		OR		EAX,0x00000001	; bit0を1にする（プロテクトモード移行のため）
		MOV		CR0,EAX
		JMP		pipelineflush	;在设置了PE位以后，初始代码要立即执行一条JMP指令，以刷新处理器预取指令队列。80386会在使用前预取、解码指令和地址。但是，当切换到保护模式时，预取的指令将不再有效（属于实模式的）。JMP指令将会使处理器罢弃无效的信息。
pipelineflush:	;设置可读写的段
		MOV		AX,1*8			;  読み書き可能セグメント32bit
		MOV		DS,AX
		MOV		ES,AX
		MOV		FS,AX
		MOV		GS,AX
		MOV		SS,AX

; bootpackの転送

		MOV		ESI,bootpack	; 転送元
		MOV		EDI,BOTPAK		; 転送先
		MOV		ECX,512*1024/4
		CALL	memcpy

; ついでにディスクデータも本来の位置へ転送

; まずはブートセクタから

		MOV		ESI,0x7c00		; 転送元
		MOV		EDI,DSKCAC		; 転送先
		MOV		ECX,512/4
		CALL	memcpy

; 残り全部

		MOV		ESI,DSKCAC0+512	; 転送元
		MOV		EDI,DSKCAC+512	; 転送先
		MOV		ECX,0
		MOV		CL,BYTE [CYLS]
		IMUL	ECX,512*18*2/4	; シリンダ数からバイト数/4に変換
		SUB		ECX,512/4		; IPLの分だけ差し引く
		CALL	memcpy

; asmheadでしなければいけないことは全部し終わったので、
;	あとはbootpackに任せる

; bootpackの起動

		MOV		EBX,BOTPAK
		MOV		ECX,[EBX+16]
		ADD		ECX,3			; ECX += 3;
		SHR		ECX,2			; ECX /= 4;
		JZ		skip			; 転送するべきものがない
		MOV		ESI,[EBX+20]	; 転送元
		ADD		ESI,EBX
		MOV		EDI,[EBX+12]	; 転送先
		CALL	memcpy
skip:
		MOV		ESP,[EBX+12]	; スタック初期値
		JMP		DWORD 2*8:0x0000001b

waitkbdout:
		IN		 AL,0x64
		AND		 AL,0x02
		JNZ		waitkbdout		; ANDの結果が0でなければwaitkbdoutへ
		RET

memcpy:	;将软盘内容复制到内存
		MOV		EAX,[ESI]
		ADD		ESI,4
		MOV		[EDI],EAX
		ADD		EDI,4
		SUB		ECX,1
		JNZ		memcpy			; 引き算した結果が0でなければmemcpyへ
		RET
; memcpyはアドレスサイズプリフィクスを入れ忘れなければ、ストリング命令でも書ける

		ALIGNB	16
GDT0:
		RESB	8				; ヌルセレクタ
		DW		0xffff,0x0000,0x9200,0x00cf	; 読み書き可能セグメント32bit
		DW		0xffff,0x0000,0x9a28,0x0047	; 実行可能セグメント32bit（bootpack用）

		DW		0
GDTR0:
		DW		8*3-1
		DD		GDT0

		ALIGNB	16
bootpack:
