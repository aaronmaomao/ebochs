/*
 * keyboard.c
 *
 *  Created on: 2018��8��27��
 *      Author: aaron
 */
#include "mwos.h"

/**
 * �ж�keyboard��·�Ƿ�׼����
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
 * ��ʼ��keyboard��·Ϊ0x47ģʽ
 */
void init_keyboard() {
	wait_KBC_sendready();
	io_out8(PORT_KEYBOARD_CMD, KEYBOARD_WRITE_MODE);
	wait_KBC_sendready();
	io_out8(PORT_KEYBOARD_DAT, KEYBOARD_MODE); //��ģʽ�£�keyboard��·�ᴥ������ж�
	return;
}

/** �����жϴ����� */
void inthandler21(int* esp) {
	uchar data, str[4];
	BOOTINFO* binfo = (BOOTINFO*) 0x0ff0;
	io_out8(PIC0_OCW2, 0x60 + 0x1);	//�ж��ܸ����
	data = io_in8(PORT_KEYBOARD_DAT);	//��ȡ���̶˿ڵ�����
	sprintf(str, "%02x", data);
	putfont8Str(binfo->vram, binfo->scrnx, 10, 30, COL8_00FF00, str);
	return;
}
