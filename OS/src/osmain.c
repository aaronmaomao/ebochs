/*
 * osmain.c
 *
 *  Created on: 2018��8��18��
 *      Author: mao-zhengjun
 */

#include "mwos.h"
#include "stdio.h"

void HariMain(void) {
	uchar mouse_buf[16 * 16];
	uchar fifo_buf[512];
	FIFO8 fifo;
	MOUSE_DEC mouse_dec;
	BOOTINFO* binfo = (BOOTINFO*) BINFO_ADDR;
	init_gdtidt();
	init_pic();
	init_keyboard();
	enable_mouse(&mouse_dec);
	io_sti();

	init_palette();
	init_screen8(binfo->vram, binfo->scrnx, binfo->scrny);
	initMouseCursor(mouse_buf, COL8_008484);
	putblock(binfo->vram, binfo->scrnx, 50, 50, 16, 16, mouse_buf, 16);

	io_out8(PIC0_IMR, 0xf9);	//ʹ�ܼ����ж�
	io_out8(PIC1_IMR, 0xef);	//ʹ������ж�
	for (;;)
		io_stihlt();
}

