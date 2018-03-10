package com.mwos.ebochs.view;

import org.eclipse.swt.graphics.Image;
import org.eclipse.wb.swt.ResourceManager;

public abstract class PersonCategory implements Comparable<Person> {
	private String categoryName;
	private int priority;
	private static PersonCategory types[] = {
			new PersonCategory("陌生人",1) {
				@Override
				public Image getImage() {
					return ResourceManager.getPluginImage("com.mwos.ebochs", "resource/image/window.png");
				}},
			new PersonCategory("同事",2) {
				@Override
				public Image getImage() {
					return ResourceManager.getPluginImage("com.mwos.ebochs", "resource/image/panda.png");
				}
			},
			new PersonCategory("家人",3) {
				@Override
				public Image getImage() {
					return ResourceManager.getPluginImage("com.mwos.ebochs", "resource/image/register.png");
				}
			}
	};

	public PersonCategory(String categoryName, int priority) {
		this.categoryName = categoryName;
		this.priority = priority;
	}

	@Override
	public int compareTo(Person o) {
		return this.priority - o.getCategory().priority;
	}

	public abstract Image getImage();

}
