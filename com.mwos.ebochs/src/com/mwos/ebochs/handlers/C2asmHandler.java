package com.mwos.ebochs.handlers;

import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.commands.IHandler;
import org.eclipse.core.commands.IHandlerListener;

public class C2asmHandler implements IHandler {

	@Override
	public void addHandlerListener(IHandlerListener handlerListener) {
		// TODO Auto-generated method stub
		System.out.println(1);
	}

	@Override
	public void dispose() {
		// TODO Auto-generated method stub
		System.out.println(2);
	}

	@Override
	public Object execute(ExecutionEvent event) throws ExecutionException {
		// TODO Auto-generated method stub
		System.out.println(3);
		return null;
	}

	@Override
	public boolean isEnabled() {
		// TODO Auto-generated method stub
		System.out.println(4);
		return true;
	}

	@Override
	public boolean isHandled() {
		// TODO Auto-generated method stub\
		System.out.println(5);
		return true;
	}

	@Override
	public void removeHandlerListener(IHandlerListener handlerListener) {
		// TODO Auto-generated method stub
		System.out.println(6);

	}

}
