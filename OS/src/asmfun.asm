;汇编函数定义，提供给C语言调用
[FORMAT "WCOFF"]
[INSTRSET "i486p"]	;使用到486为止的指令集
[BITS 32]		;制作32位模式的机器语言

[FILE "asmfun.asm"]	;制作目标文件信息
	GLOBAL	_io_hlt, _io_load_eflags, _io_store_eflags, _io_cli, _io_sti, _io_in8, _io_out8, _load_gdtr, _load_idtr
	GLOBAL	_io_stihlt
	GLOBAL _asm_inthandler21, _asm_inthandler2c

	EXTERN _inthandler21, _inthandler2c
;实际函数
[section .text]
_io_hlt:
	hlt
	ret
_io_load_eflags:	;io_load_eflags(void)
	pushfd		;指push eflags寄存器，此操作会将eflags压入栈中
	pop	eax	;将栈顶元素弹出，放入eax中
	ret
_io_store_eflags:	;void io_store_eflags(int eflags)
	mov	eax,	[esp+4]
	push	eax
	popfd		;将栈顶值弹出放入eflags中
	ret
_io_cli:	;void io_cli(void)	清除中断标记位
	cli
	ret
_io_sti:	;	设置中断标记位
	sti
	ret
_io_stihlt:
	sti		;sti和hlt如果连续执行，CPU会忽略之间发生的中断
	hlt
	ret
_io_out8:	;io_out8(int port, int data)
	mov	edx,	[esp+4]
	mov	al,	[esp+8]
	out 	dx,	al
	ret
_io_in8:	;int io_in8(int port)
	mov	edx,	[esp+4]
	mov	eax,	0
	in	al,	dx
	ret
_load_gdtr:		; void load_gdtr(int limit, int addr);
	MOV	AX,[ESP+4]		; limit
	MOV	[ESP+6],AX
	LGDT	[ESP+6]
	RET

_load_idtr:		; void load_idtr(int limit, int addr);
	MOV	AX,[ESP+4]		; LIMIT
	MOV	[ESP+6],AX
	LIDT	[ESP+6]
	RET

_asm_inthandler21:	;键盘中断
	PUSH	ES
	PUSH	DS
	PUSHAD
	MOV		EAX,ESP
	PUSH	EAX
	MOV		AX,SS
	MOV		DS,AX
	MOV		ES,AX
	CALL	_inthandler21
	POP		EAX
	POPAD
	POP		DS
	POP		ES
	IRETD
_asm_inthandler2c:	;鼠标中断
	PUSH	ES
	PUSH	DS
	PUSHAD
	MOV		EAX,ESP
	PUSH	EAX
	MOV		AX,SS
	MOV		DS,AX
	MOV		ES,AX
	CALL	_inthandler2c
	POP		EAX
	POPAD
	POP		DS
	POP		ES
	IRETD
