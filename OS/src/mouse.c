/*
 * mouse.c
 *
 *  Created on: 2018年8月27日
 *      Author: aaron
 */

#include "mwos.h"

/**
 * 使能鼠标
 */
void enable_mouse(MOUSE_DEC* mouse_dec) {
	wait_KBC_sendready();
	io_out8(PORT_KEYBOARD_CMD, PORT_KEYBOARD_TO_MOUSE);	//如果向keyboard电路发送0xd4, 下一个数据会发往鼠标
	wait_KBC_sendready();
	io_out8(PORT_KEYBOARD_DAT, PORT_KEYBOARD_ENABLE_MOUSE);
	mouse_dec->phase = 0;		//等待0xfa数据阶段
	return;
}

#define MOUSE_CODE_ERR -1
#define MOUSE_CODE_NO	0
#define MOUSE_CODE_OK 	1
int mouse_decode(MOUSE_DEC* mdec, uchar dat) {
	if (mdec->phase == 0) {
		if (dat == 0xfa) {
			mdec->phase = 1;
		}
		return MOUSE_CODE_NO;
	}
	if (mdec->phase == 1) {
		mdec->buf[0] = dat;
		mdec->phase = 2;
		return MOUSE_CODE_NO;
	}
	if (mdec->phase == 2) {
		mdec->buf[1] = dat;
		mdec->phase = 3;
		return MOUSE_CODE_NO;
	}
	if (mdec->phase == 3) {
		mdec->buf[2] = dat;
		mdec->phase = 1;
		return MOUSE_CODE_OK;
	}
	return MOUSE_CODE_ERR;
}

/** 鼠标中断处理函数 */
void inthandler2c(int* esp) {
	uchar dat, data[4];
	BOOTINFO* binfo = (BOOTINFO*) BINFO_ADDR;
	io_out8(PIC1_OCW2, 0x64);
	io_out8(PIC0_OCW2, 0x62);
	dat = io_in8(PORT_KEYBOARD_DAT);
	//fifo8_put(fifo, data)
	sprintf(data, "%02x");
	putfont8Str(binfo->vram, binfo->scrnx, 100, 100, COL8_848400, data);
	return;
}
