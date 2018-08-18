CYLS	EQU	10

ORG	0x7C00

JMP 	entry
DB	0X90
DB	"HELLOIPL"		; �֩`�ȥ���������ǰ�����ɤ˕����Ƥ褤��8�Х��ȣ�
DW	512				; 1�������δ󤭤���512�ˤ��ʤ���Ф����ʤ���
DB	1				; ���饹���δ󤭤���1�������ˤ��ʤ���Ф����ʤ���
DW	1				; FAT���ɤ�����ʼ�ޤ뤫����ͨ��1������Ŀ����ˤ��룩
DB	2				; FAT�΂�����2�ˤ��ʤ���Ф����ʤ���
DW	224				; ��`�ȥǥ��쥯�ȥ��I��δ󤭤�����ͨ��224����ȥ�ˤ��룩
DW	2880			; ���Υɥ饤�֤δ󤭤���2880�������ˤ��ʤ���Ф����ʤ���
DB	0xf0			; ��ǥ����Υ����ף�0xf0�ˤ��ʤ���Ф����ʤ���
DW	9				; FAT�I����L����9�������ˤ��ʤ���Ф����ʤ���
DW	18				; 1�ȥ�å��ˤ����ĤΥ����������뤫��18�ˤ��ʤ���Ф����ʤ���
DW	2				; �إåɤ�����2�ˤ��ʤ���Ф����ʤ���
DD	0				; �ѩ`�ƥ�������ʹ�äƤʤ��ΤǤ����ϱؤ�0
DD	2880			; ���Υɥ饤�ִ󤭤���⤦һ�ȕ���
DB	0,0,0x29		; �褯�狼��ʤ����ɤ��΂��ˤ��Ƥ����Ȥ����餷��
DD	0xffffffff		; ���֤�ܥ��`�ॷ�ꥢ�뷬��
DB	"HELLO-OS   "	; �ǥ���������ǰ��11�Х��ȣ�
DB	"FAT12   "		; �ե��`�ޥåȤ���ǰ��8�Х��ȣ�
RESB	18				; �Ȥꤢ����18�Х��Ȥ����Ƥ���

entry:
MOV	AX,	0
MOV	SS,	AX
MOV	SP,	0x7C00
MOV	DS,	AX

MOV	AX,	0x0820
MOV	ES,	AX
MOV	CH,	0	;0����
MOV	DH,	0	;0��ͷ
MOV	CL,	2	;2����

readloop:
MOV	SI,	0	;��¼ʧ�ܴ����ļĴ���

retry:
MOV	AH,	0x02	;read disk
MOV	AL,	1	;
MOV	BX,	0
MOV	DL,	0x00
INT	0X13
JNC	next
ADD	SI,	1
CMP	SI, 	5
JAE	err
MOV	AH,	0x00
MOV	DL,	0x00
INT	0x13
JMP	retry

next:
mov 	ax,	es
add	ax,	0x20	;�μ�0x20������ַ������512Byte
mov	es,	ax
add	cl,	1
cmp	cl,	18
jbe	readloop
mov	cl,	1
add	dh,	1
cmp	dh,	2
jb	readloop
mov	dh,	0
add	ch,	1
cmp	ch,	CYLS
jb	readloop

mov	[0x0ff0],	ch
jmp	0xc200	;��ȷ�Ķ��������е�����

fin:
HLT
JMP	fin

err:
MOV	SI,	msg

printf:
MOV	AL,	[SI]
ADD	SI,	1
CMP	AL,	0
JE	fin
MOV	AH,	0x0E
MOV	BX,	15
INT	0X10
JMP	printf

msg:
DB	0x0A, 0x0A
DB	"READ DISK ERROR!"
DB	0x0A
DB	0

RESB	0x7dfe-$
DB	0x55, 0xAA

;��䲿��
