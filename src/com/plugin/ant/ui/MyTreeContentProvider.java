package com.plugin.ant.ui;

import java.util.List;

import org.eclipse.jface.viewers.ITreeContentProvider;

import com.plugin.ant.model.Smell;

public class MyTreeContentProvider implements ITreeContentProvider{

	@Override
	public Object[] getElements(Object inputElement) {
		// TODO Auto-generated method stub
		if(inputElement instanceof List) {
			List input = (List)inputElement;
			return input.toArray();
		}
		return new Object[] {inputElement};
	}

	@Override
	public Object[] getChildren(Object parentElement) {
		// TODO Auto-generated method stub
		if(parentElement instanceof Smell) {
			Smell smell = (Smell) parentElement;
			List children = smell.getClassList();
			return children.toArray();
		}
		return new Object[0];
	}

	@Override
	public Object getParent(Object element) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean hasChildren(Object element) {
		// TODO Auto-generated method stub
		if(element instanceof Smell) {
			Smell smell = (Smell) element;
			List children = smell.getClassList();
			return !(children==null || children.isEmpty());
		}
		return false;
	}

}
