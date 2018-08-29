/*
 * keyboard.c
 *
 *  Created on: 2018��8��27��
 *      Author: aaron
 */
#include "mwos.h"

FIFO8* key_fifo;

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
void init_keyboard(FIFO8* fifo) {
	key_fifo = fifo;
	wait_KBC_sendready();
	io_out8(PORT_KEYBOARD_CMD, KEYBOARD_WRITE_MODE);
	wait_KBC_sendready();
	io_out8(PORT_KEYBOARD_DAT, KEYBOARD_MODE); //��ģʽ�£�keyboard��·�ᴥ������ж�
	return;
}

/** �����жϴ����� */
void inthandler21(int* esp) {
	uchar data;
	io_out8(PIC0_OCW2, 0x60 + 0x1);	//�ж��ܸ����
	data = io_in8(PORT_KEYBOARD_DAT);	//��ȡ���̶˿ڵ�����
//	fifo8_put(key_fifo, data);
	return;
}
