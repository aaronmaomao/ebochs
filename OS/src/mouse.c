/*
 * mouse.c
 *
 *  Created on: 2018��8��27��
 *      Author: aaron
 */

#include "mwos.h"
FIFO8* mouse_fifo;
/**
 * ʹ�����
 */
void enable_mouse(FIFO8* fifo, MOUSE_DEC* mouse_dec) {
	mouse_fifo = fifo;
	wait_KBC_sendready();
	io_out8(PORT_KEYBOARD_CMD, PORT_KEYBOARD_TO_MOUSE);	//�����keyboard��·����0xd4, ��һ�����ݻᷢ�����
	wait_KBC_sendready();
	io_out8(PORT_KEYBOARD_DAT, PORT_KEYBOARD_ENABLE_MOUSE);
	mouse_dec->phase = 0;		//�ȴ�0xfa���ݽ׶�
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
		if ((dat & 0xc8) == B_00001000) {
			mdec->buf[0] = dat;
			mdec->phase = 2;
		}
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

		mdec->btn = mdec->buf[0] & B_00000111;	//buf[0]����λ�ǰ���ֵ
		mdec->x = mdec->buf[1];
		mdec->y = mdec->buf[2];

		if ((mdec->buf[0] & 0x10) != 0) {
			mdec->x |= 0xffffff00;
		}
		if ((mdec->buf[0] & 0x20) != 0) {
			mdec->y |= 0xffffff00;
		}
		mdec->y = -mdec->y;

		return MOUSE_CODE_OK;
	}
	return MOUSE_CODE_ERR;
}

/** ����жϴ����� */
void inthandler2c(int* esp) {
	uchar dat;
	io_out8(PIC1_OCW2, 0x64);
	io_out8(PIC0_OCW2, 0x62);
	dat = io_in8(PORT_KEYBOARD_DAT);
	fifo8_put(mouse_fifo, dat);
	return;
}
