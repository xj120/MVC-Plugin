package com.plugin.ant.util;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.mvcRstr.model.ClassStructure;
import com.mvcRstr.model.OdorClassRs;
import com.mvcRstr.model.OdorResult;
import com.mvcRstr.util.JsonUtil;
import com.mvc_str.core.RunTest;
import com.mvc_str.core.utils.DealFile;
import com.plugin.ant.model.SmellClass;
import com.plugin.ant.model.SmellType;

public class MvcSmellAnalyze {
	private RunTest runTest=new RunTest();
	private JsonUtil jsonUtil=new JsonUtil();
	private DealFile dealFile=new DealFile();
	
	//MVC依赖图
	public String getMvcStruct(String path) throws IOException{
		return runTest.get_MvcStructResult(path);
	}
	
	//异味类集合
    public String getMvcSmell() throws IOException {
        return runTest.get_OrdorResult();
    }
    
    //生成json文件
    public String[] generateJson(String path) throws Exception {
    	String ret=this.getMvcStruct(path);
    	String smell=this.getMvcSmell();
    	
    	String retPath=path+"\\back-end"+dealFile.getTime()+".json";
    	String smellPath=path+"\\back-end_odr_res"+dealFile.getTime()+".json";
    	
    	dealFile.createFile(retPath);
    	dealFile.writeString(retPath, ret);
    	
    	dealFile.createFile(smellPath);
    	dealFile.writeString(smellPath, smell);
    	
    	return new String[] {retPath, smellPath};
    }
    
    //返回service缺失类
    public List<SmellClass> getServiceMissingClasses(String path) throws Exception{
    	List<SmellClass> smellClasses=new ArrayList<SmellClass>();
    	OdorResult odorResult = jsonUtil.readJson(path);
    	List<OdorClassRs> serviceMissing_Classes=odorResult.getService_Missing_Class();
    	for(OdorClassRs smclass:serviceMissing_Classes) {
    		smellClasses.add(new SmellClass(SmellType.SERVICE_MISSING, smclass.getAllclassName(), smclass.getRole()));
    	}
		return smellClasses;
    }
    
    //返回角色职责混淆类
    public List<SmellClass> getRoleViolationClasses(String path) throws Exception{
    	List<SmellClass> smellClasses=new ArrayList<SmellClass>();
    	OdorResult odorResult = jsonUtil.readJson(path);
    	List<OdorClassRs> roleViolation_Classes=odorResult.getRole_Violation_Class();
    	for(OdorClassRs rvclass:roleViolation_Classes) {
    		smellClasses.add(new SmellClass(SmellType.ROLE_VIOLATION, rvclass.getAllclassName(), rvclass.getRole()));
    	}
		return smellClasses;	
    }
    
    public List<OdorClassRs> getRoleViolationOdorClassRs(String path) throws Exception{
    	OdorResult odorResult = jsonUtil.readJson(path);
    	List<OdorClassRs> classList = odorResult.getRole_Violation_Class();
    	return classList;
    }
    
    public ClassStructure getCstruct(String path) throws Exception{
    	return jsonUtil.readMvcStruct(path);
    }
}
