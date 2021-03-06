/*
 * int.c
 *
 *  Created on: 2018年8月22日
 *      Author: aaron
 */

#include "mwos.h"

/** 初始化pic可编程中断控制器 */
void init_pic(void) {
	io_out8(PIC0_IMR, 0xff);	//屏蔽所有的中断
	io_out8(PIC1_IMR, 0xff);

	io_out8(PIC0_ICW1, 0x11);	//边沿触发模式,	固定（与主板接线有关）
	io_out8(PIC0_ICW2, 0x20);	//0x20表示，IRQ0~7由INT20~27接收，注：INT0x00~0x1f用于CPU内部的中断
	io_out8(PIC0_ICW3, 1 << 2);	//即0000 0100，PIC1由IRQ2连接
	io_out8(PIC0_ICW4, 0x01);	//无缓冲模式，固定（与主板接线有关）

	io_out8(PIC1_ICW1, 0x11);	//边沿触发模式，固定（与主板接线有关）
	io_out8(PIC1_ICW2, 0x28);	//0x28表示，IRQ8~15由INT28~2f接收
	io_out8(PIC1_ICW3, 2);		//PIC1由IRQ2连接
	io_out8(PIC1_ICW4, 0x01);	//无缓冲模式，固定（与主板接线有关）

	io_out8(PIC0_IMR, 0xfb);	//1111 1011, 除IRQ2外的禁止
	io_out8(PIC1_IMR, 0xff);	//禁止PIC1所有的中断
	return;
}

