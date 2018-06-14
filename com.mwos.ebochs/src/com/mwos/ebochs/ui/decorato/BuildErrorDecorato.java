package com.mwos.ebochs.ui.decorato;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.jface.viewers.IDecoration;
import org.eclipse.jface.viewers.ILabelDecorator;
import org.eclipse.jface.viewers.ILabelProviderListener;
import org.eclipse.jface.viewers.ILightweightLabelDecorator;
import org.eclipse.swt.graphics.Image;

import com.mwos.ebochs.Activator;

public class BuildErrorDecorato implements ILabelDecorator, ILightweightLabelDecorator {

	@Override
	public void addListener(ILabelProviderListener listener) {
		// TODO Auto-generated method stub

	}

	@Override
	public void dispose() {
		// TODO Auto-generated method stub

	}

	@Override
	public boolean isLabelProperty(Object element, String property) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void removeListener(ILabelProviderListener listener) {
		// TODO Auto-generated method stub

	}

	@Override
	public Image decorateImage(Image image, Object element) {
		
		if(element instanceof IFile) {
			try {
				if(((IFile) element).findMarkers("com.ebochs.BuildErrorMarker", false, IFile.DEPTH_INFINITE).length!=0) {
					return Activator.getImage("resource/icons/error-small.png");
				}
			} catch (CoreException e) {
				e.printStackTrace();
			};
		}
		return null;
	}

	@Override
	public String decorateText(String text, Object element) {
		
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void decorate(Object element, IDecoration decoration) {
		decoration.addOverlay(Activator.getPluginImageDescriptor("resource/icons/basket_go.png"), IDecoration.TOP_RIGHT);
		decoration.addPrefix("My Prefix ");
		decoration.addSuffix("My Suffix");

	}

}
