/*
 * keyboard.c
 *
 *  Created on: 2018年8月27日
 *      Author: aaron
 */
#include "mwos.h"

/**
 * 判断keyboard电路是否准备好
 */
void wait_KBC_sendready() {
	while (1) {
		if ((io_in8(PORT_KEYBOARD_STA) & 0x02) == 0) {
			break;
		}
	}
	return;
}

/**
 * 初始化keyboard电路为0x47模式
 */
void init_keyboard() {
	wait_KBC_sendready();
	io_out8(PORT_KEYBOARD_CMD, KEYBOARD_WRITE_MODE);
	wait_KBC_sendready();
	io_out8(PORT_KEYBOARD_DAT, KEYBOARD_MODE); //该模式下，keyboard电路会触发鼠标中断
	return;
}

/** 键盘中断处理函数 */
void inthandler21(int* esp) {
	uchar data, str[4];
	BOOTINFO* binfo = (BOOTINFO*) 0x0ff0;
	io_out8(PIC0_OCW2, 0x60 + 0x1);	//中断受付完毕
	data = io_in8(PORT_KEYBOARD_DAT);	//读取键盘端口的数据
	sprintf(str, "%02x", data);
	putfont8Str(binfo->vram, binfo->scrnx, 10, 30, COL8_00FF00, str);
	return;
}
