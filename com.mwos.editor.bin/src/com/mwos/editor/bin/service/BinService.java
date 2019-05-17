package com.mwos.editor.bin.service;

import java.io.File;
import java.io.FileOutputStream;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.mwos.editor.bin.model.Byte16Item;

/**
 * 
 * @author maozhengjun
 * @time 2019年4月24日 上午10:19:53
 */
public class BinService {
	private int offset;
	private File file;
	private ArrayList<Byte16Item> items;

	public BinService(File file) {
		this(Utils.readFile(file), 0);
		this.file = file;
	}

	public BinService(byte[] data, int offset) {
		this.offset = offset;
		items = new ArrayList<>(data.length / 16 + 2);
		pack(data, items);
	}

	public Byte16Item[] getItems() {
		return items.toArray(new Byte16Item[] {});
	}

	public Byte16Item[] getItems(int startOffset, int endOffset, boolean ignoreZero) {
		startOffset = startOffset < offset ? offset : startOffset;
		endOffset = endOffset > (offset + getSize() - 1) ? (offset + getSize() - 1) : endOffset;
		int startIndex = calculateIndex(startOffset);
		int endIndex = calculateIndex(endOffset);

		List<Byte16Item> _items = new ArrayList<>();
		for (int i = startIndex; i <= endIndex; i++) {
			_items.add(items.get(i));
		}

		if (ignoreZero && _items.size() > 2) {
			int zeroCount = 0;
			for (int i = _items.size() - 2; i >= 1; i--) {

				if (_items.get(i).getValues().compareTo(BigInteger.ZERO) == 0) {
					zeroCount++;
				} else {
					if (zeroCount > 1) {
						for (int j = (i + zeroCount); j > i; j--) {
							_items.remove(j);
						}
						_items.add(i + 1, new Byte16Item(-1, new Byte[16]));
					}

					zeroCount = 0;
				}
			}
			if (zeroCount > 1) {
				for (int j = (zeroCount); j > 0; j--) {
					_items.remove(j);
				}
				_items.add(1, new Byte16Item(-1, new Byte[16]));
			}
		}
		return _items.toArray(new Byte16Item[] {});
	}

	public int getOffset() {
		return offset;
	}

	public int getSize() {
		int itemNum = items.size();
		int length = itemNum * 0x10;
		length -= offset % 0x10;
		int endShift = 0x10;
		Byte[] last = items.get(itemNum - 1).getBytes();
		for (; endShift > 0; endShift--) {
			if (last[endShift - 1] != null) {
				break;
			}
		}
		length -= (0x10 - endShift);
		return length;
	}

	public boolean save() {
		return save(this.file, 0x00, getSize());
	}

	public boolean save(File file, int start, int length) {
		try {
			FileOutputStream fo = new FileOutputStream(file);
			byte[] data = getData(start, length);
			fo.write(data, 0, data.length);
			fo.flush();
			fo.close();
		} catch (Exception e) {
			e.printStackTrace();
		}

		return true;
	}

	public byte[] getData(int startOffset, int length) {
		startOffset = startOffset < offset ? offset : startOffset;
		startOffset = startOffset > (offset + length - 1) ? offset + length - 1 : startOffset;
		length = length > getSize() - (startOffset - offset) ? getSize() - (startOffset - offset) : length;

		int startIndex = calculateIndex(offset);
		int endIndex = calculateIndex(startOffset + length - 1);

		int startShift = startOffset % 0x10;
		byte[] data = new byte[length];
		for (int i = startOffset + 1; i < endIndex; i++) {
			int base = (i - startOffset-1) * 0x10 + 0x10- startShift;
			Byte[] itemData = items.get(i).getBytes();
			for (int j = 0; j < 0x10; j++) {
				data[base + j] = itemData[j];
			}
		}
		
		if (startIndex != endIndex) {
			Byte[] itemData = items.get(startIndex).getBytes();
			for (int i = 0; i < (0x10 - startOffset); i++) {
				data[i] = itemData[i + startOffset];
			}

			itemData = items.get(endIndex).getBytes();
			int base = (endIndex - startIndex - 1) * 0x10 + (0x10 - startShift);
			for (int i = 0; i < 0x10; i++) {
				if (itemData[i] != null) {
					data[base + i] = itemData[i];
				} else {
					break;
				}
			}
		} else {
			Byte[] itemData = items.get(startIndex).getBytes();
			for (int i = 0; i < length; i++) {
				data[i] = itemData[i + startOffset];
			}
		}

		return data;
	}

	private int calculateIndex(int addr) {
		return (addr / 16) - (offset / 16);
	}

	private void pack(byte[] data, ArrayList<Byte16Item> items) {
		int nextItemOffset = offset;
		Byte16Item item;
		if ((nextItemOffset % 0x10) != 0) {
			int shift = 0x10 - (nextItemOffset % 0x10);
			nextItemOffset -= shift;
			Byte itemData[] = new Byte[16];
			for (int i = shift; i < 16; i++) {
				itemData[i] = data[i - shift];
			}

			item = new Byte16Item(nextItemOffset, itemData);
			items.add(item);
			nextItemOffset += 0x10;
		}

		while ((data.length - (nextItemOffset - offset)) >= 16) {
			Byte itemData[] = new Byte[16];
			for (int i = 0; i < 16; i++) {
				itemData[i] = data[nextItemOffset - offset + i];
			}
			item = new Byte16Item(nextItemOffset, itemData);
			items.add(item);
			nextItemOffset += 0x10;
		}

		if (data.length != (nextItemOffset - offset)) {
			Byte itemData[] = new Byte[16];
			for (int i = 0; i < (data.length - (nextItemOffset - offset)); i++) {
				itemData[i] = data[nextItemOffset - offset + i];
			}
			item = new Byte16Item(nextItemOffset, itemData);
			items.add(item);
		}
	}
}
