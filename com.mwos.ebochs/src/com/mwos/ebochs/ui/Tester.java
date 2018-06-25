package com.mwos.ebochs.ui;

import org.eclipse.core.expressions.PropertyTester;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.ui.part.FileEditorInput;

import com.mwos.ebochs.resource.project.OSProjectNature;

public class Tester extends PropertyTester {

	public Tester() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public boolean test(Object receiver, String property, Object[] args, Object expectedValue) {
		// TODO Auto-generated method stub
	
		if(receiver instanceof FileEditorInput) {
			try {
				if(((FileEditorInput) receiver).getFile().getProject().getNature(OSProjectNature.NatureId)!=null) {
					return true;
				}
			} catch (CoreException e) {
				e.printStackTrace();
			}
		}
		
		if(receiver instanceof IProject) {
			try {
				if(((IProject) receiver).getNature(OSProjectNature.NatureId)!=null){
					return true;
				}
			} catch (CoreException e) {
				e.printStackTrace();
			}
		}
			
		return false;
	}

}
