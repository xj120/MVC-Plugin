package com.plugin.ant.actions;


import java.io.File;

import javax.swing.JFileChooser;

import org.eclipse.core.internal.resources.Project;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.IPath;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.IWorkbenchWindowActionDelegate;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;

import com.plugin.ant.util.MvcSmellAnalyze;
import com.plugin.ant.views.MvcAnalyzeView;

public class MvcAnalyzeAction implements IWorkbenchWindowActionDelegate {
	private IWorkbenchWindow mWindow;

    @Override
    public void run(IAction action) {

    	mWindow = PlatformUI.getWorkbench().getActiveWorkbenchWindow();
    	IWorkbenchPage page = mWindow.getActivePage();
    	if(page != null) {
    		try {
    	        IProject prj = getCurrentProject();
    	        if (prj != null) {
    	            IPath rootPath = prj.getLocation();
    	            System.out.println("Project root path: " + rootPath.toOSString());
    	            // 这里可以对项目根路径进行相应的处理
    	            MvcAnalyzeView.projectPath=rootPath.toOSString();
    	            System.out.println(MvcAnalyzeView.projectPath);
    	        }
				page.showView("com.plugin.ant.views.MvcAnalyzeView");
			} catch (PartInitException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
    	}

    }
    
    private IProject getCurrentProject() {
        IWorkbenchWindow window = PlatformUI.getWorkbench().getActiveWorkbenchWindow();
        if (window != null) {
            ISelection selection = window.getSelectionService().getSelection();
            if (selection instanceof IStructuredSelection) {
                Object firstElement = ((IStructuredSelection) selection).getFirstElement();
                System.out.println(firstElement.getClass().getName());
                if (firstElement.getClass().getName().equals("org.eclipse.core.internal.resources.Project")) {
                    // 获取 Project 对应的路径
                    IResource resource = ResourcesPlugin.getWorkspace().getRoot().findMember(((Project) firstElement).getFullPath());
                    if (resource != null && resource.getProject() != null) {
                        return resource.getProject();
                    }
                }

//                if (firstElement instanceof Project) {
//                    return (IProject) firstElement;
//                }
            }
        }
        return null;
    }

    @Override
    public void selectionChanged(IAction action, ISelection selection) {
    }

    @Override
    public void dispose() {
    }

    @Override
    public void init(IWorkbenchWindow window) {
        this.mWindow = window;
    }
}
