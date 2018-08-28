/*
 * mouse.c
 *
 *  Created on: 2018��8��27��
 *      Author: aaron
 */

#include "mwos.h"

/**
 * ʹ�����
 */
void enable_mouse(MOUSE_DEC* mouse_dec) {
	wait_KBC_sendready();
	io_out8(PORT_KEYBOARD_CMD, PORT_KEYBOARD_TO_MOUSE);	//�����keyboard��·����0xd4, ��һ�����ݻᷢ�����
	wait_KBC_sendready();
	io_out8(PORT_KEYBOARD_DAT, PORT_KEYBOARD_ENABLE_MOUSE);
	mouse_dec->phase = 0;		//�ȴ�0xfa���ݽ׶�
	return;
}

/** ����жϴ����� */
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
