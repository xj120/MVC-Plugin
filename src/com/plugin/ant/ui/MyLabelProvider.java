package com.plugin.ant.ui;

import org.eclipse.jface.viewers.ILabelProvider;
import org.eclipse.jface.viewers.ILabelProviderListener;
import org.eclipse.swt.graphics.Image;

import com.plugin.ant.model.Smell;
import com.plugin.ant.model.SmellClass;

public class MyLabelProvider implements ILabelProvider{

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
	public Image getImage(Object element) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getText(Object element) {
		// TODO Auto-generated method stub
		if(element instanceof Smell) {
			Smell smell = (Smell) element;
			return smell.getSmellType();
		}
		else if(element instanceof SmellClass) {
			SmellClass smellClass = (SmellClass) element;
			return smellClass.getAllName();
		}
		return null;
	}

}
