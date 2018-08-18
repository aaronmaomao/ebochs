;汇编函数定义，提供给C语言调用
[FORMAT "WCOFF"]
[INSTRSET "i486p"]	;使用到486为止的指令集
[BITS 32]		;制作32位模式的机器语言

[FILE "asmfun.asm"]	;制作目标文件信息
	GLOBAL	_io_hlt
;实际函数
[section .text]
_io_hlt:
	hlt
	ret
