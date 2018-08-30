/*
 * osmain.c
 *
 *  Created on: 2018年8月18日
 *      Author: mao-zhengjun
 */

#include "mwos.h"
#include "stdio.h"

void HariMain(void) {
	uchar dat, temp[40];
	uchar mouse_buf[16 * 16];
	uchar fifo_buf[512];
	int mx = 0, my = 0;
	FIFO8 fifo;
	MOUSE_DEC mouse_dec;
	BOOTINFO* binfo = (BOOTINFO*) BINFO_ADDR;
	init_fifo8(&fifo, 512, fifo_buf);

	init_gdtidt();
	init_pic();
	init_keyboard(&fifo);
	enable_mouse(&fifo, &mouse_dec);
	io_sti();

	init_palette();
	init_screen8(binfo->vram, binfo->scrnx, binfo->scrny);
	initMouseCursor(mouse_buf, COL8_008484);
	putblock(binfo->vram, binfo->scrnx, mx, my, 16, 16, mouse_buf, 16);

	io_out8(PIC0_IMR, 0xf9);	//使能键盘中断
	io_out8(PIC1_IMR, 0xef);	//使能鼠标中断
	for (;;) {
		io_cli();
		if (fifo8_status(&fifo) == 0) {
			io_stihlt();
		} else {
			dat = fifo8_get(&fifo);
			io_sti();
			if (mouse_decode(&mouse_dec, dat) == MOUSE_CODE_OK) {
				sprintf(temp, "[lcr] %4d %4d", mouse_dec.x, mouse_dec.y);
				if ((mouse_dec.btn & B_00000001) != 0) {
					temp[1] = 'L';
				}
				if ((mouse_dec.btn & B_00000010) != 0) {
					temp[3] = 'R';
				}
				if ((mouse_dec.btn & B_00000100) != 0) {
					temp[2] = 'C';
				}
				boxfill8(binfo->vram, binfo->scrnx, COL8_008484, 32, 16, 32 + 15 * 8 - 1, 31);
				putfont8Str(binfo->vram, binfo->scrnx, 32, 16, COL8_0000FF, temp);

				boxfill8(binfo->vram, binfo->scrnx, COL8_008484, mx, my, mx + 15, my + 15);
				mx += mouse_dec.x;
				my += mouse_dec.y;
				if (mx < 0) {
					mx = 0;
				}
				if (my < 0) {
					my = 0;
				}

				if (mx > binfo->scrnx - 16) {
					mx = binfo->scrnx - 16;
				}
				if (my > binfo->scrny - 16) {
					my = binfo->scrny - 16;
				}
				putblock(binfo->vram, binfo->scrnx, 16, , bxsize, bysize, buf, bufxSize);
			}

		}
	}
}

