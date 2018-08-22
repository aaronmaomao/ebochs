/*
 * destbl.c
 *
 *  Created on: 2018��8��22��
 *      Author: aaron
 */
#include "mwos.h"

void init_gdtidt() {
	GDT* gdt = (GDT*) ADR_GDT;
	IDT* idt = (IDT*) ADR_IDT;
	int i;
	for (i = 0; i < 8192; i++) {
		setgdt(gdt + i, 0, 0, 0);
	}
	setgdt(gdt + 1, 0xffffffff, 0x00000000, AR_DATA32_RW);
	setgdt(gdt + 2, 0x0007ffff, 0x00280000, AR_CODE32_ER);
	load_gdtr(LIMIT_GDT, (int)gdt);	//0xffff��GDTռ�õ���Ч�ֽ���-1

	for (i = 0; i < 256; i++) {
		setidt(idt + i, 0, 0, 0);
	}
	load_idtr(LIMIT_IDT, (int)idt);
	setidt(idt+0x21, (int)asm_inthandler21, 2*8, AR_INTGATE32);
	setidt(idt+0x2c, (int)asm_inthandler2c, 2*8, AR_INTGATE32);
	return;
}

void setgdt(GDT* gdt, uint limit, uint base, int ar) {
	if (limit > 0xfffff) {	//���ڴ���������1M
		ar |= 0x8000; /* G_bit = 1 */
		limit /= 0x1000;	//������λ����ȥ����limit_high�ĸ�3λ
	}
	gdt->limit_low = limit & 0xffff;
	gdt->base_low = base & 0xffff;
	gdt->base_mid = (base >> 16) & 0xff;
	gdt->access_right = ar & 0xff;
	gdt->limit_high = ((limit >> 16) & 0x0f) | ((ar >> 8) & 0xf0);
	gdt->base_high = (base >> 24) & 0xff;
	return;
}

void setidt(IDT* idt, uint offset, int selector, int ar) {
	idt->offset_low = offset & 0xffff;
	idt->selector = selector;
	idt->dw_count = (ar >> 8) & 0xff;
	idt->access_right = ar & 0xff;
	idt->offset_high = (offset >> 16) & 0xffff;
	return;
}
