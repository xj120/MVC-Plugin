package com.plugin.ant.util;

import java.io.IOException;
import java.util.List;

import com.mvcRstr.SMiss_ReStruct;
import com.mvcRstr.analysis.ProcessAly;
import com.mvcRstr.model.ClassStructure;
import com.mvcRstr.model.CodeR.BlockLine;
import com.mvcRstr.model.DDG.DataDependenceGraphVo;
import com.mvcRstr.util.JsonUtil;

public class ServiceMissingUtil {
	
	SMiss_ReStruct serviceRestruct = new SMiss_ReStruct();
	JsonUtil jsonUtil = new JsonUtil();
	
	public String[] serviceMissingRestructClass(String jsonPath, String classPath, String projectPath, String allName) throws Exception {
		String serviceCode = "";
		String controllerCode = "";
        ClassStructure cstruct=jsonUtil.readMvcStruct(jsonPath);
        List<String> strlist=serviceRestruct.getRepositoryClasses(cstruct,projectPath,allName);
        for(String repository:strlist){
            if(repository!=null) {
            	serviceRestruct.repositoryName=repository;
            }
            serviceCode = serviceRestruct.newServiceMissing_Restruct(classPath,repository,projectPath);
            System.out.println(serviceCode);
        }
        //System.out.println("!!------------------------------------------!!");
        controllerCode = serviceRestruct.getNewControllerCode();
        //System.out.println(controllerCode);
        return new String[] {serviceCode, controllerCode};
	}
	
	//返回异味代码行数组
	public List<BlockLine> getBlockLines(String classPath) throws Exception{
    	List<int []> methodList;
    	ProcessAly analysis=new ProcessAly();
    	List<int[]> senList=analysis.getStructBlock(classPath);
    	methodList=analysis.getMethodArea(classPath,senList);
    	DataDependenceGraphVo dependenceGraphVo = analysis.get_CDG(classPath);
    	List<BlockLine> statem_Lines=serviceRestruct.createMethodList_SMiss(dependenceGraphVo,methodList, senList);
		return statem_Lines;
	}
}
