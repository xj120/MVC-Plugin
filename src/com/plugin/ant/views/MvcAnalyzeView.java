package com.plugin.ant.views;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;

import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.part.ViewPart;

import com.plugin.ant.ui.AdviceWindow;
import com.plugin.ant.ui.CompareWindow;
import com.mvcRstr.SMiss_ReStruct;
import com.mvcRstr.model.ClassStructure;
import com.mvcRstr.model.OdorClassRs;
import com.mvcRstr.model.CodeR.BlockLine;
import com.mvcRstr.util.JsonUtil;
import com.plugin.ant.model.SmellClass;
import com.plugin.ant.model.SmellType;
import com.plugin.ant.ui.MyLabelProvider;
import com.plugin.ant.ui.MyTreeContentProvider;
import com.plugin.ant.util.FileUtil;
import com.plugin.ant.util.MvcSmellAnalyze;
import com.plugin.ant.util.RoleViolationUtil;
import com.plugin.ant.util.ServiceMissingUtil;
import com.plugin.ant.util.TreeViewerUtil;

public class MvcAnalyzeView extends ViewPart{
	
	private TreeViewer treeViewer;
	private MvcSmellAnalyze mvcSmellAnalyze = new MvcSmellAnalyze();
	private ServiceMissingUtil srmUtil = new ServiceMissingUtil();
	private RoleViolationUtil rvlUtil = new RoleViolationUtil();
	private TreeViewerUtil treeViewerUtil = new TreeViewerUtil();
	private FileUtil fileUtil = new FileUtil();
	
	public static String projectPath = "D:\\JavaCode\\autodeploy_oldcopy\\back-end\\";
	
	MyTreeContentProvider myTreeContentProvider = new MyTreeContentProvider();
    MyLabelProvider myLabelProvider = new MyLabelProvider();
	
//	String projectPath="D:\\JavaCode\\autodeploy_oldcopy\\back-end\\";
	String jsonPath="D:\\Eclipse\\eclipse\\MvcPlugin\\src\\back-end202403181919.json";
	String odorPath="D:\\Eclipse\\eclipse\\MvcPlugin\\src\\back-end_odr_res202403181919.json";
	String allName="com.processmining.logdeploy.autodeploy.controller.ProjectController";
	String classPath="D:\\JavaCode\\autodeploy_oldcopy\\back-end\\src\\main\\java\\com\\processmining\\logdeploy\\autodeploy\\controller\\ProjectController.java";
	
	@Override
	public void createPartControl(Composite parent){
		// TODO Auto-generated method stub
		
        Composite child = new Composite(parent, SWT.NONE);

        GridLayout layout = new GridLayout();
        layout.marginHeight = 5;
        layout.marginWidth = 5;
        layout.numColumns = 1;
        child.setLayout(layout);
        
        treeViewer = new TreeViewer(child, SWT.H_SCROLL);
        GridData treeViewerGridData = new GridData(SWT.FILL, SWT.FILL, true, true);
        treeViewer.getControl().setLayoutData(treeViewerGridData);
        
        

        Button button = new Button(child, SWT.PUSH);
        button.setText("Code Refactor");
        

        GridData gridData = new GridData(SWT.CENTER, SWT.END, true, true);
        button.setLayoutData(gridData);
        
        
        treeViewer.setContentProvider(myTreeContentProvider);
        treeViewer.setLabelProvider(myLabelProvider);
        
        List<SmellClass> serviceMissingClasses = new ArrayList<SmellClass>();
        List<SmellClass> roleViolationClasses = new ArrayList<SmellClass>();
        

//        List<OdorClassRs> roleViolationOdorClasses = new ArrayList<OdorClassRs>();
//        ClassStructure cstruct = null;
        
        try {
        	System.out.println(projectPath);
            String[] paths = mvcSmellAnalyze.generateJson(projectPath);
            jsonPath=paths[0];
            odorPath=paths[1];
			serviceMissingClasses = mvcSmellAnalyze.getServiceMissingClasses(odorPath);
			roleViolationClasses = mvcSmellAnalyze.getRoleViolationClasses(odorPath);
//	        roleViolationOdorClasses = mvcSmellAnalyze.getRoleViolationOdorClassRs(odorPath);
//	        cstruct = mvcSmellAnalyze.getCstruct(jsonPath);
			treeViewer.setInput(treeViewerUtil.getTree(serviceMissingClasses, roleViolationClasses));
		} catch (Exception e1) {
			AdviceWindow window = new AdviceWindow("异味检测失败！");
			window.runWindow();
			e1.printStackTrace();
		}
        
        
        treeViewer.refresh();
        
        treeViewer.addSelectionChangedListener(new ISelectionChangedListener() {
            @Override
            public void selectionChanged(SelectionChangedEvent event) {
                IStructuredSelection selection = (IStructuredSelection) event.getSelection();
                Object selectedNode = selection.getFirstElement();
            }
        });
        
		
        button.addSelectionListener(new SelectionAdapter() {
            @Override
            public void widgetSelected(SelectionEvent e) {
            	IStructuredSelection selection = (IStructuredSelection) treeViewer.getSelection();
            	Object selectedNode = selection.getFirstElement();
            	if(selectedNode instanceof SmellClass) {
            		SmellClass smellClass = (SmellClass) selectedNode;
            		if(smellClass.getOdorType()==SmellType.SERVICE_MISSING) {	
            			try {
            				allName=smellClass.getAllName();
            				classPath=fileUtil.getJavaPath(projectPath, allName);
            				String[] ret=srmUtil.serviceMissingRestructClass(jsonPath, classPath, projectPath, allName);
            				List<BlockLine> blockLines = srmUtil.getBlockLines(classPath);
            				List<Integer> lines=fileUtil.setToList(blockLines);
							final String ctrCode=fileUtil.readJavaFile(classPath);							
							final String serviceCode=ret[0];
							final String newCtrCode=ret[1];
							final String modifiedCtrCode=fileUtil.addAnnotation(newCtrCode, blockLines);

	                    	SwingUtilities.invokeLater(new Runnable() {
	                            public void run() {
	                                CompareWindow window = new CompareWindow(modifiedCtrCode, ctrCode, serviceCode, lines, classPath);
	                                window.setVisible(true);
	                                window.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
	                            }
	                        });
						} catch (Exception e1) {
							AdviceWindow window = new AdviceWindow("重构失败！");
							window.runWindow();
							e1.printStackTrace();
						}
            			System.out.println("service missing");
            		}
            		else if(smellClass.getOdorType()==SmellType.ROLE_VIOLATION) {
            			try {
                			allName=smellClass.getAllName();
                			final List<OdorClassRs> roleViolationOdorClasses = mvcSmellAnalyze.getRoleViolationOdorClassRs(odorPath);
                			final ClassStructure cstruct = mvcSmellAnalyze.getCstruct(jsonPath);
                			OdorClassRs odorClass = rvlUtil.getOdorClassByName(roleViolationOdorClasses, allName);
							String advice = rvlUtil.roleViolateRestructClass(odorClass, cstruct, projectPath);
							AdviceWindow window = new AdviceWindow(advice);
							window.runWindow();
						} catch (Exception e1) {
							// TODO Auto-generated catch block
							AdviceWindow window = new AdviceWindow("重构失败！");
							window.runWindow();
							e1.printStackTrace();
						}
            			System.out.println("role violation");
            		}
            	}
            	treeViewer.refresh();
                System.out.println("Mvc Refactor");
            }
        });
	}

	@Override
	public void setFocus() {
		// TODO Auto-generated method stub
		
	}


	@Override
	public Object getAdapter(Class arg0) {
		// TODO Auto-generated method stub
		return null;
	}

}
