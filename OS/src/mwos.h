/*
 * mwos.h
 *
 *  Created on: 2018Äê8ÔÂ18ÈÕ
 *      Author: mao-zhengjun
 */

#ifndef MWOS_H_
#define MWOS_H_

#define uchar unsigned char
#define uint unsigned int

/* naskfunc.nas */
void io_hlt(void);
int io_load_eflags(void);
void io_store_eflags(int eflags);
void io_cli(void);
void io_sti(void);
int io_in8(int port);
void io_out8(int port, int data);

void write_mem8(int addr, int data);

void init_palette();
void set_palette(int start, int end, uchar *rgb);


#endif /* MWOS_H_ */
