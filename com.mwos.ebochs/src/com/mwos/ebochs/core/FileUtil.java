package com.mwos.ebochs.core;

import java.io.File;
import java.io.FileFilter;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class FileUtil {
	private FileUtil() {

	}

	/**
	 * 查找文件
	 * 
	 * @param path
	 *            目录
	 * @param reg
	 *            文件名
	 * @return
	 */
	public static File searchFile(String path, String reg) {
		File f = new File(path);
		if (f.exists() && f.isDirectory()) {
			File[] childs = f.listFiles();
			for (File child : childs) {
				if (child.getName().matches(reg)) {
					return child;
				}
			}
		}
		return null;
	}

	/**
	 * 列出指定目录下的所有文件
	 * 
	 * @param dirctory
	 *            目录
	 * @param filter
	 *            要筛选的正则字符串
	 * @param filterInner
	 *            返回选择器之中的还是之外的
	 * @return 返回<文件名:文件>集合
	 */
	public static Map<String, File> mapFiles(String dirctory, String[] filter, boolean filterInner) {
		List<File> files = new ArrayList<>();
		File dir = new File(dirctory);
		if (dir.exists() && dir.isDirectory()) {
			recurFile(dir, files, new FileFilter() {
				@Override
				public boolean accept(File pathname) {
					for (String fn : filter) {
						if (pathname.isDirectory())
							return true;
						if (pathname.getName().matches(fn))
							return filterInner;
					}
					return !filterInner;
				}
			});
		}
		if (files.size() == 0) {
			return null;
		}
		Map<String, File> map = new LinkedHashMap<>();
		for (File file : files) {
			map.put(file.getName(), file);
		}
		return map;
	}

	/**
	 * 列出指定目录下的所有文件
	 * 
	 * @param dirctory
	 *            目录
	 * @param filter
	 *            要筛选的正则字符串
	 * @return 返回<文件名:文件>集合
	 */
	public static Map<String, File> mapFiles(String dirctory, String[] filter) {
		return mapFiles(dirctory, filter, false);
	}

	/**
	 * 列出指定目录下的所有文件
	 * 
	 * @param dirctory
	 *            目录
	 * @param filter
	 *            要过滤的文件名
	 * @return 返回文件集合
	 */
	public static List<File> listFiles(String dirctory, String[] filter, boolean filterInner) {
		List<File> files = new ArrayList<>();
		File dir = new File(dirctory);
		if (dir.exists() && dir.isDirectory()) {
			recurFile(dir, files, new FileFilter() {

				@Override
				public boolean accept(File pathname) {
					for (String fn : filter) {
						if (pathname.isDirectory())
							return true;
						if (pathname.getName().matches(fn))
							return filterInner;
					}
					return !filterInner;
				}
			});
		}
		if (files.size() == 0) {
			return null;
		}
		return files;
	}

	/**
	 * 列出指定目录下的所有文件
	 * 
	 * @param dirctory
	 *            目录
	 * @param filter
	 *            要过滤的文件名
	 * @return 返回文件集合
	 */
	public static List<File> listFiles(String dirctory, String[] filter) {
		return listFiles(dirctory, filter, false);
	}

	public static List<File> listDir(String dirctory, String[] filter, boolean filterInner) {
		List<File> files = new ArrayList<>();
		File dir = new File(dirctory);
		if (dir.exists() && dir.isDirectory()) {
			recurDir(dir, files, new FileFilter() {

				@Override
				public boolean accept(File pathname) {
					if (pathname.isFile())
						return false;
					for (String fn : filter) {
						if (pathname.getName().matches(fn))
							return filterInner;
					}
					return !filterInner;
				}
			});
		}
		return files;
	}

	public static List<File> listDir(String dirctory, String[] filter) {
		return listDir(dirctory, filter, false);
	}

	public static List<File> listDir(String dirctory) {
		return listDir(dirctory, new String[] {}, false);
	}

	/**
	 * 递归查找文件
	 * 
	 * @param file
	 * @param list
	 * @param filter
	 */
	private static void recurFile(File file, List<File> list, FileFilter filter) {
		if (file.isDirectory()) {
			File[] files = file.listFiles(filter);
			for (File f : files) {
				recurFile(f, list, filter);
			}
		} else {
			list.add(file);
		}
	}

	private static void recurDir(File file, List<File> list, FileFilter filter) {
		File[] files = file.listFiles(filter);
		for (File dir : files) {
			list.add(dir);
			recurDir(dir, list, filter);
		}
	}

	public static String getFileName(String path, boolean ext) {
		File f = new File(path);
		String s[] = f.getAbsolutePath().split("\\\\");
		if (ext) {
			return s[s.length - 1];
		} else {
			String temp = s[s.length - 1];
			if (temp.contains("."))
				return temp.substring(0, temp.lastIndexOf("."));
			else
				return temp;
		}
	}

	public static String getIncStr(String path) {
		List<File> incs = listDir(path, new String[] { "obj", ".setting" }, false);
		String temp = "";
		for (File f : incs) {
			temp += (" \"" + f.getAbsolutePath() + "\"");
		}
		return temp;
	}

	public static void main(String[] args) {
		String a = "asdfsdf";
		System.out.println(a.split("\\\\").length);
	}

}
