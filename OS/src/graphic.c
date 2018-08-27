/*
 * graphic.c
 *
 *  Created on: 2018年8月22日
 *      Author: aaron
 */

#include "mwos.h"

#define PORT_PALETTE_START 0x03c8
#define PORT_PALETTE_COLOR 0x03c9

void init_screen8(uchar* vram, int x, int y) {
	boxfill8(vram, x, COL8_008484, 0, 0, x - 1, y - 1);	//桌面背景
	boxfill8(vram, x, COL8_C6C6C6, 0, y - 30, x - 1, y - 1);
	boxfill8(vram, x, COL8_FFFFFF, (x - 32) / 2, y - 29, (x - 32) / 2 + 32 + 1, y - 29);
	boxfill8(vram, x, COL8_FFFFFF, (x - 32) / 2, y - 29, (x - 32) / 2, y - 2);
	boxfill8(vram, x, COL8_C6C6C6, (x - 32) / 2 + 1, y - 28, (x - 32) / 2 + 1 + 32, y - 2);
	boxfill8(vram, x, COL8_848484, (x - 32) / 2 + 1, y - 2, (x - 32) / 2 + 1 + 32, y - 2);
	boxfill8(vram, x, COL8_848484, (x - 32) / 2 + 1 + 32, y - 28, (x - 32) / 2 + 1 + 32, y - 2);

	putfont8Str(vram, x, 10, 10, COL8_000000, "Mao Zhengjun!");
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
	io_out8(PORT_PALETTE_START, start);		//告诉显卡要设置颜色序号了
	for (i = start; i <= end; i++) {
		io_out8(PORT_PALETTE_COLOR, rgb[0] / 4);		//设置序号对应的颜色
		io_out8(PORT_PALETTE_COLOR, rgb[1] / 4);
		io_out8(PORT_PALETTE_COLOR, rgb[2] / 4);
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
	static char cursor[16][16] = { "**************..", "*OOOOOOOOOOO*...", "*O*O*O*O*OO*....", "*OOOOOOOOO*.....", "*O*O*O*OO*......",
			"*OOOOOOO*.......", "*O*O*O*O*.......", "*OOOOOOOO*......", "*O*OO**OOO*.....", "*OOO*..*OOO*....", "*OO*....*OOO*...",
			"*O*......*OOO*..", "**........*OOO*.", "*..........*OOO*", "............*OO*", ".............***" };

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

