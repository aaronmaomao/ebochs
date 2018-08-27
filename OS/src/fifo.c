/*
 * fifo.c
 *
 *  Created on: 2018年8月27日
 *      Author: aaron
 */
#include "mwos.h"

#define FIFO_OVERRUN 0x0001
#define FIFO_BLANK 0x0000

void init_fifo8(FIFO8* fifo, int size, uchar* buf) {
	fifo->size = size;
	fifo->buf = buf;
	fifo->free = size;
	fifo->flags = FIFO_BLANK;
	fifo->wp = 0;
	fifo->rp = 0;
	return;
}

/**
 * 给fifo添加数据
 */
int fifo8_put(FIFO8* fifo, uchar data) {
	if (fifo->free == 0) {
		fifo->flags |= FIFO_OVERRUN;
		return -1;
	}
	fifo->buf[fifo->wp] = data;
	fifo->wp++;
	if (fifo->wp == fifo->size) {
		fifo->wp = 0;
	}
	fifo->free--;
	return 0;
}

/**
 * 获取数据
 */
int fifo8_get(FIFO8* fifo) {
	int data;
	if (fifo->free == fifo->size) {
		return -1;
	}
	data = fifo->buf[fifo->rp];
	fifo->rp++;
	if (fifo->rp == fifo->size) {
		fifo->rp = 0;
	}
	fifo->free++;
	return data;
}

int fifo8_status(FIFO8* fifo) {
	return fifo->size - fifo->free;
}
