package com.plugin.ant.ui;

import javax.swing.JOptionPane;

public class AdviceWindow {
	String advice;
	public AdviceWindow(String advice) {
		this.advice=advice;
	}
	
	public void runWindow() {
		JOptionPane.showMessageDialog(null, this.advice, "角色混淆异味修改建议", JOptionPane.INFORMATION_MESSAGE);
	}

}
