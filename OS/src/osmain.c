/*
 * osmain.c
 *
 *  Created on: 2018��8��18��
 *      Author: mao-zhengjun
 */

#include "mwos.h"

void HariMain(void) {
	int i;
	uchar *p;
	init_palette();
	p = (uchar*) 0xa0000;
	for (i = 0; i <= 0xffff; i++) {
		p[i] = i & 0x0f;
	}
	for (;;)
		io_hlt();
}

/** ��ʼ���Կ��ĵ�ɫ��  */
void init_palette() {
	static uchar table_rgb[16 * 3] = { 0x00, 0x00, 0x00, /*  0:�\ */
	0xff, 0x00, 0x00, /*  1:���뤤�� */
	0x00, 0xff, 0x00, /*  2:���뤤�v */
	0xff, 0xff, 0x00, /*  3:���뤤��ɫ */
	0x00, 0x00, 0xff, /*  4:���뤤�� */
	0xff, 0x00, 0xff, /*  5:���뤤�� */
	0x00, 0xff, 0xff, /*  6:���뤤ˮɫ */
	0xff, 0xff, 0xff, /*  7:�� */
	0xc6, 0xc6, 0xc6, /*  8:���뤤��ɫ */
	0x84, 0x00, 0x00, /*  9:������ */
	0x00, 0x84, 0x00, /* 10:�����v */
	0x84, 0x84, 0x00, /* 11:������ɫ */
	0x00, 0x00, 0x84, /* 12:������ */
	0x84, 0x00, 0x84, /* 13:������ */
	0x00, 0x84, 0x84, /* 14:����ˮɫ */
	0x84, 0x84, 0x84 /* 15:������ɫ */
	};
	set_palette(0, 15, table_rgb);
	return;
}

/** �����Կ��ĵ�ɫ����� */
void set_palette(int start, int end, uchar *rgb) {
	int i, eflags;
	eflags = io_load_eflags();
	io_cli();
	io_out8(0x03c8, start);		//�����Կ�Ҫ������ɫ�����
	for (i = start; i <= end; i++) {
		io_out8(0x03c9, rgb[0] / 4);		//������Ŷ�Ӧ����ɫ
		io_out8(0x03c9, rgb[1] / 4);
		io_out8(0x03c9, rgb[2] / 4);
		rgb += 3;
	}
	io_store_eflags(eflags);
	return;

}
