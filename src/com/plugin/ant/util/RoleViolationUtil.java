package com.plugin.ant.util;

import java.util.List;

import com.mvcRstr.RCC_Restruct;
import com.mvcRstr.model.ClassStructure;
import com.mvcRstr.model.OdorClassRs;
import com.mvcRstr.model.OdorResult;
import com.mvcRstr.util.JsonUtil;

public class RoleViolationUtil {
	RCC_Restruct roleRestruct = new RCC_Restruct();
	JsonUtil jsonUtil = new JsonUtil();
	public List<OdorClassRs> roleViolateRestruct(String projectPath, String path, String odorPath) throws Exception{
		OdorResult odorResult = jsonUtil.readJson(odorPath);
		
		//ClassStructure cstruct = jsonUtil.readMvcStruct(path);
		
		List<OdorClassRs> roleViolationClasses = odorResult.getRole_Violation_Class();
		return roleViolationClasses;
	}
	
	public OdorClassRs getOdorClassByName(List<OdorClassRs> classList, String allName) {
		for(OdorClassRs odorClass : classList) {
			if(odorClass.getAllclassName().equals(allName)) {
				return odorClass;
			}
		}
		return null;
	}
	
	//返回角色职责混淆异味结果
	public String roleViolateRestructClass(OdorClassRs odorClass, ClassStructure cstruct, String projectPath) throws Exception {
		return roleRestruct.runRcc_Restruct(cstruct, odorClass, projectPath);
	}
}
