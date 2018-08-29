/*
 * mwos.h
 *
 *  Created on: 2018年8月18日
 *      Author: mao-zhengjun
 */

#ifndef MWOS_H_
#define MWOS_H_

#define uchar unsigned char
#define uint unsigned int
#define ushort unsigned short
#define BINFO_ADDR 0x0ff0
#define B_00000001		1
#define B_00000010		2
#define B_00000011		3
#define B_00000100		4
#define B_00000111		7
#define B_00001000		8
#define B_00010000		16
#define B_00100000		32
#define B_01000000		64
#define B_10000000		128
typedef struct BOOTINFO {
	uchar cyls, leds, vmode, reserve;
	ushort scrnx, scrny;
	uchar* vram;
} BOOTINFO;

typedef struct GDT {
	short limit_low, base_low;
	char base_mid, access_right;
	char limit_high, base_high;	//limit_high的高4位会存储段属性
} GDT;
typedef struct IDT {
	short offset_low, selector;
	char dw_count, access_right;
	short offset_high;
} IDT;

extern uchar font[2048];

/* naskfunc.nas */
void io_hlt(void);
int io_load_eflags(void);
void io_store_eflags(int eflags);
void io_cli(void);
void io_sti(void);
void io_stihlt(void);
int io_in8(int port);
void io_out8(int port, int data);
void load_gdtr(int limit, int addr);
void load_idtr(int limit, int addr);
void asm_inthandler21();
void asm_inthandler2c();

#define COLOR uchar
#define COL8_000000		0	/** black */
#define COL8_FF0000		1
#define COL8_00FF00		2
#define COL8_FFFF00		3
#define COL8_0000FF		4
#define COL8_FF00FF		5
#define COL8_00FFFF		6
#define COL8_FFFFFF		7	/*white*/
#define COL8_C6C6C6		8
#define COL8_840000		9
#define COL8_008400		10
#define COL8_848400		11
#define COL8_000084		12
#define COL8_840084		13
#define COL8_008484		14
#define COL8_848484		15
void init_screen8(uchar* vram, int x, int y);
void init_palette();
void set_palette(int start, int end, uchar *rgb);
void boxfill8(uchar* vram, int xsize, uchar color, int x0, int y0, int x1, int y1);
void putfont8(uchar* vram, int xsize, int x, int y, uchar color, char* font);
void putfont8Str(uchar* vram, int xsize, int x, int y, uchar color, uchar* str);
/** 初始化鼠标的图像缓冲区 */
void initMouseCursor(char* mouse, COLOR bcolor);
/** 显示块图  */
void putblock(uchar* vram, int vxsize, int px0, int py0, int pxsize, int pysize, uchar* buf, int bxsize);

#define ADR_IDT			0x0026f800	//idt的地址
#define ADR_GDT			0x00270000	//gdt的地址
#define LIMIT_IDT		0x000007ff	//idt的大小(256*8-1)
#define LIMIT_GDT		0x0000ffff	//idt的大小(2^13*8-1)
#define ADR_OSMAIN		0x00280000	//内核代码的起始地址
#define LIMIT_BOTPAK	0x0007ffff
#define AR_DATA32_RW	0x4092		//系统专用，可读可写
#define AR_CODE32_ER	0x409a		//系统专用，可执行
#define AR_INTGATE32	0x008e		//用于中断处理的有效设定
#define AR_TSS32		0x0089
#define AR_LDT			0x0082
void init_gdtidt();
void setgdt(GDT* gdt, uint limit, uint base, int ar);
void setidt(IDT* idt, uint offset, int selector, int ar);

/** int.c */
#define PIC0_ICW1		0x0020
#define PIC0_OCW2		0x0020
#define PIC0_IMR		0x0021
#define PIC0_ICW2		0x0021
#define PIC0_ICW3		0x0021
#define PIC0_ICW4		0x0021
#define PIC1_ICW1		0x00a0
#define PIC1_OCW2		0x00a0
#define PIC1_IMR		0x00a1
#define PIC1_ICW2		0x00a1
#define PIC1_ICW3		0x00a1
#define PIC1_ICW4		0x00a1
void init_pic();

void inthandler21(int* esp);
void inthandler2c(int* esp);

/**
 * fifo
 */
typedef struct FIFO8 {
	uchar* buf;
	uint wp, rp, size, free, flags;
} FIFO8;

void init_fifo8(FIFO8* fifo, int size, uchar* buf);
int fifo8_put(FIFO8* fifo, uchar data);
int fifo8_get(FIFO8* fifo);
int fifo8_status(FIFO8* fifo);

/**
 * keyboard
 */
#define PORT_KEYBOARD_STA 	0x0064	//键盘电路的状态读取端口
#define PORT_KEYBOARD_CMD 	0x0064	//键盘电路的命令设置端口
#define PORT_KEYBOARD_DAT 	0x0060	//键盘电路的数据存取端口
#define KEYBOARD_WRITE_MODE 0x60	//给键盘电路写模式命令
#define KEYBOARD_MODE 		0x47	//该模式下，keyboard电路会触发鼠标中断
void wait_KBC_sendready();
void init_keyboard(FIFO8* fifo);

/**
 * mouse
 */
#define MOUSE_CODE_ERR -1
#define MOUSE_CODE_NO	0
#define MOUSE_CODE_OK 	1

typedef struct MOUSE_DEC {
	uchar buf[3], phase;
	int x, y, btn;
} MOUSE_DEC;

#define PORT_KEYBOARD_TO_MOUSE  0xd4
#define PORT_KEYBOARD_ENABLE_MOUSE  0xf4
void enable_mouse(FIFO8* fifo, MOUSE_DEC* mouse_dec);
int mouse_decode(MOUSE_DEC* mdec, uchar dat);

#endif /* MWOS_H_ */
