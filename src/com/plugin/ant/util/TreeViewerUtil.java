package com.plugin.ant.util;

import java.util.ArrayList;
import java.util.List;

import com.plugin.ant.model.Smell;
import com.plugin.ant.model.SmellClass;
import com.plugin.ant.model.SmellType;

public class TreeViewerUtil {
	
	//设置输入
	public List<Smell> getTree(List<SmellClass> serviceMissingClasses, List<SmellClass> roleViolateClasses){
		List<Smell> smells=new ArrayList<Smell>();
		Smell serviceMissing = new Smell(SmellType.SERVICE_MISSING, serviceMissingClasses, "Service Missing Class List");
		Smell roleViolation = new Smell(SmellType.ROLE_VIOLATION, roleViolateClasses, "Role Violation Class List");
		smells.add(serviceMissing);
		smells.add(roleViolation);
		return smells;
	}
}
