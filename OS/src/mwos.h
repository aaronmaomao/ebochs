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

#endif /* MWOS_H_ */
