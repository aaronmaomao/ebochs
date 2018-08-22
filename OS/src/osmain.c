/*
 * osmain.c
 *
 *  Created on: 2018年8月18日
 *      Author: mao-zhengjun
 */

#include "mwos.h"
#include "stdio.h"

void HariMain(void) {
	BOOTINFO* binfo = (BOOTINFO*) 0x0ff0;
	uchar mouse_buf[16 * 16];

	init_palette();
	boxfill8(binfo->vram, binfo->scrnx, COL8_008484, 0, 0, binfo->scrnx - 1, binfo->scrny - 1);	//桌面背景
	boxfill8(binfo->vram, binfo->scrnx, COL8_C6C6C6, 0, binfo->scrny - 30, binfo->scrnx - 1, binfo->scrny - 1);
	boxfill8(binfo->vram, binfo->scrnx, COL8_FFFFFF, (binfo->scrnx - 32) / 2, binfo->scrny - 29, (binfo->scrnx - 32) / 2 + 32 + 1,
			binfo->scrny - 29);
	boxfill8(binfo->vram, binfo->scrnx, COL8_FFFFFF, (binfo->scrnx - 32) / 2, binfo->scrny - 29, (binfo->scrnx - 32) / 2,
			binfo->scrny - 2);
	boxfill8(binfo->vram, binfo->scrnx, COL8_C6C6C6, (binfo->scrnx - 32) / 2 + 1, binfo->scrny - 28,
			(binfo->scrnx - 32) / 2 + 1 + 32, binfo->scrny - 2);
	boxfill8(binfo->vram, binfo->scrnx, COL8_848484, (binfo->scrnx - 32) / 2 + 1, binfo->scrny - 2,
			(binfo->scrnx - 32) / 2 + 1 + 32, binfo->scrny - 2);
	boxfill8(binfo->vram, binfo->scrnx, COL8_848484, (binfo->scrnx - 32) / 2 + 1 + 32, binfo->scrny - 28,
			(binfo->scrnx - 32) / 2 + 1 + 32, binfo->scrny - 2);

	putfont8Str(binfo->vram, binfo->scrnx, 10, 10, COL8_000000, "Mao Zhengjun!");

	initMouseCursor(mouse_buf, COL8_008484);
	putblock(binfo->vram, binfo->scrnx, 50, 50, 16, 16, mouse_buf, 16);
	for (;;)
		io_hlt();
}

/** 初始化显卡的调色板  */
void init_palette() {
	static uchar table_rgb[16 * 3] = { 0x00, 0x00, 0x00, /*  0:黒 */
	0xff, 0x00, 0x00, /*  1:明るい赤 */
	0x00, 0xff, 0x00, /*  2:明るい緑 */
	0xff, 0xff, 0x00, /*  3:明るい黄色 */
	0x00, 0x00, 0xff, /*  4:明るい青 */
	0xff, 0x00, 0xff, /*  5:明るい紫 */
	0x00, 0xff, 0xff, /*  6:明るい水色 */
	0xff, 0xff, 0xff, /*  7:白 */
	0xc6, 0xc6, 0xc6, /*  8:明るい灰色 */
	0x84, 0x00, 0x00, /*  9:暗い赤 */
	0x00, 0x84, 0x00, /* 10:暗い緑 */
	0x84, 0x84, 0x00, /* 11:暗い黄色 */
	0x00, 0x00, 0x84, /* 12:暗い青 */
	0x84, 0x00, 0x84, /* 13:暗い紫 */
	0x00, 0x84, 0x84, /* 14:暗い水色 */
	0x84, 0x84, 0x84 /* 15:暗い灰色 */
	};
	set_palette(0, 15, table_rgb);
	return;
}

/** 设置显卡的调色板序号 */
void set_palette(int start, int end, uchar *rgb) {
	int i, eflags;
	eflags = io_load_eflags();
	io_cli();
	io_out8(0x03c8, start);		//告诉显卡要设置颜色序号了
	for (i = start; i <= end; i++) {
		io_out8(0x03c9, rgb[0] / 4);		//设置序号对应的颜色
		io_out8(0x03c9, rgb[1] / 4);
		io_out8(0x03c9, rgb[2] / 4);
		rgb += 3;
	}
	io_store_eflags(eflags);
	return;
}

void boxfill8(uchar* vram, int xsize, uchar color, int x0, int y0, int x1, int y1) {
	int x, y;
	for (y = y0; y <= y1; y++) {
		for (x = x0; x <= x1; x++) {
			vram[y * xsize + x] = color;
		}
	}
	return;
}

void putfont8(uchar* vram, int xsize, int x, int y, uchar color, char* font) {
	uchar i, d;
	uchar* p;
	for (i = 0; i < 16; i++) {
		p = vram + (i + y) * xsize + x;
		d = font[i];
		if ((d & 0x80) != 0) {
			p[0] = color;
		}
		if ((d & 0x40) != 0) {
			p[1] = color;
		}
		if ((d & 0x20) != 0) {
			p[2] = color;
		}
		if ((d & 0x10) != 0) {
			p[3] = color;
		}
		if ((d & 0x08) != 0) {
			p[4] = color;
		}
		if ((d & 0x04) != 0) {
			p[5] = color;
		}
		if ((d & 0x02) != 0) {
			p[6] = color;
		}
		if ((d & 0x01) != 0) {
			p[7] = color;
		}
	}
}

void putfont8Str(uchar* vram, int xsize, int x, int y, uchar color, uchar* str) {
	while (*str != 0) {
		putfont8(vram, xsize, x, y, color, font + *str * 16);
		str++;
		x += 8;
	}
}

/** 初始化鼠标的图像缓冲区 */
void initMouseCursor(char* mouse, COLOR bcolor) {
	static char cursor[16][16] = { "**************..", "*OOOOOOOOOOO*...", "*O*O*O*O*OO*....", "*OOOOOOOOO*.....",
			"*O*O*O*OO*......", "*OOOOOOO*.......", "*O*O*O*O*.......", "*OOOOOOOO*......", "*O*OO**OOO*.....",
			"*OOO*..*OOO*....", "*OO*....*OOO*...", "*O*......*OOO*..", "**........*OOO*.", "*..........*OOO*",
			"............*OO*", ".............***" };

	int x, y;
	for (y = 0; y < 16; y++) {
		for (x = 0; x < 16; x++) {
			if (cursor[y][x] == '*') {
				mouse[y * 16 + x] = COL8_000000;
			}
			if (cursor[y][x] == 'O') {
				mouse[y * 16 + x] = COL8_FFFFFF;
			}
			if (cursor[y][x] == '.') {
				mouse[y * 16 + x] = bcolor;
			}
		}
	}
	return;
}

/** 显示块图  */
void putblock(uchar* vram, int vxsize, int lx0, int ly0, int bxsize, int bysize, uchar* buf, int bufxSize) {
	int x, y;
	for (y = ly0; y < ly0 + bysize; y++) {
		for (x = lx0; x < lx0 + bxsize; x++) {
			vram[y * vxsize + x] = buf[(y - ly0) * bufxSize + (x - lx0)];
		}
	}
}

void init_gdtidt() {
	GDT* gdt = (GDT*) 0x00270000;
	IDT* idt = (IDT*) 0x0026f800;
	int i;
	for (i = 0; i < 8192; i++) {
		setgdt(gdt + i, 0, 0, 0);
	}
	setgdt(gdt + 1, 0xffffffff, 0x00000000, 0x4092);
	setgdt(gdt + 2, 0x0007ffff, 0x00280000, 0x409a);
	load_gdtr(0xffff, 0x270000);

	for (i = 0; i < 256; i++) {
		setidt(idt + i, 0, 0, 0);
	}
	load_idtr(0x7ff, 0x0026f800);
	return;
}

void setgdt(GDT* gdt, uint limit, uint base, int ar) {
	if (limit > 0xfffff) {	//即内存访问域大于1M
		ar |= 0x8000; /* G_bit = 1 */
		limit /= 0x1000;	//右移三位，即去掉了limit_high的高3位
	}
	gdt->limit_low = limit & 0xffff;
	gdt->base_low = base & 0xffff;
	gdt->base_mid = (base >> 16) & 0xff;
	gdt->access_right = ar & 0xff;
	gdt->limit_high = ((limit >> 16) & 0x0f) | ((ar >> 8) & 0xf0);
	gdt->base_high = (base >> 24) & 0xff;
	return;
}

void setidt(IDT* idt, int offset, int selector, int ar) {
	idt->offset_low = offset & 0xffff;
	idt->selector = selector;
	idt->dw_count = (ar >> 8) & 0xff;
	idt->access_right = ar & 0xff;
	idt->offset_high = (offset >> 16) & 0xffff;
	return;
}
