package com.plugin.ant.ui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTextPane;
import javax.swing.SwingUtilities;
import javax.swing.text.Element;
import javax.swing.text.Style;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;

import com.plugin.ant.model.SmellClass;
import com.plugin.ant.util.FileUtil;

public class CompareWindow extends JFrame{
    
    private static final long serialVersionUID = 1L;
    JPanel myPanel1 = new JPanel();
    JPanel myPanel2 =new JPanel();
    JPanel myPanel3 =new JPanel();
    JTextPane text1=new JTextPane();
    JTextPane text2=new JTextPane();
    //新增
    JTextPane text3=new JTextPane();
    
    JButton bt1 = new JButton("取消");
    JButton bt2 = new JButton("应用");
   
    
    JScrollPane scro1=new JScrollPane(text1);
    JScrollPane scro2=new JScrollPane(text2);
    JScrollPane scro3=new JScrollPane(text3);
    
    JSplitPane jSplitPane =new JSplitPane();
    JSplitPane jSplitPane2 =new JSplitPane();
    JSplitPane jSplitPane3 =new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, jSplitPane, scro3);
    
    FileUtil ft = new FileUtil();
    public boolean isModified = false;
    
    public CompareWindow(String newCtrCode, String CtrCode, String serviceCode, List<Integer> lines, String filePath) {
    	setVisible(true);

        myPanel3.add(bt1);
        myPanel3.add(bt2);
         
        this.setTitle("代码对比");
        this.setBounds(10, 10, 1520, 800);
        jSplitPane.setContinuousLayout(true);
        jSplitPane2.setContinuousLayout(true);
        jSplitPane3.setContinuousLayout(true);

        
        jSplitPane2.setOrientation(JSplitPane.VERTICAL_SPLIT);
        jSplitPane.setOrientation(JSplitPane.HORIZONTAL_SPLIT);


        jSplitPane.setLeftComponent(scro1);
        jSplitPane.setRightComponent(scro2);

        jSplitPane2.setTopComponent(myPanel3);
        jSplitPane2.setBottomComponent(jSplitPane3);
        
        // 添加标题
        JLabel titleLabel1 = new JLabel("Controller before refactor");
        JLabel titleLabel2 = new JLabel("Controller after refactor");
        JLabel titleLabel3 = new JLabel("Service new generate");
        
        JPanel titlePanel1 = new JPanel(new BorderLayout());
        titlePanel1.add(titleLabel1, BorderLayout.NORTH);
        titlePanel1.add(scro1, BorderLayout.CENTER);

        JPanel titlePanel2 = new JPanel(new BorderLayout());
        titlePanel2.add(titleLabel2, BorderLayout.NORTH);
        titlePanel2.add(scro2, BorderLayout.CENTER);

        JPanel titlePanel3 = new JPanel(new BorderLayout());
        titlePanel3.add(titleLabel3, BorderLayout.NORTH);
        titlePanel3.add(scro3, BorderLayout.CENTER);

        // 设置标题面板的背景色
        titleLabel1.setBackground(Color.LIGHT_GRAY);
        titleLabel1.setOpaque(true);
        titleLabel2.setBackground(Color.LIGHT_GRAY);
        titleLabel2.setOpaque(true);
        titleLabel3.setBackground(Color.LIGHT_GRAY);
        titleLabel3.setOpaque(true);

        // 设置标题字体
        Font titleFont = new Font("Arial", Font.BOLD, 14);
        titleLabel1.setFont(titleFont);
        titleLabel2.setFont(titleFont);
        titleLabel3.setFont(titleFont);

        // 设置标题面板大小
        titlePanel1.setPreferredSize(new Dimension(200, 100));
        titlePanel2.setPreferredSize(new Dimension(200, 100));
        titlePanel3.setPreferredSize(new Dimension(200, 100));
        
        // 将标题面板添加到分割面板中
        jSplitPane.setLeftComponent(titlePanel1);
        jSplitPane.setRightComponent(titlePanel2);
        jSplitPane3.setLeftComponent(jSplitPane);
        jSplitPane3.setRightComponent(titlePanel3);
        

        
        jSplitPane.setDividerSize(10);
        jSplitPane2.setDividerSize(5);
        jSplitPane3.setDividerSize(10);

        jSplitPane.setDividerLocation(490);
        jSplitPane3.setDividerLocation(990);
        setContentPane(jSplitPane2);
        
        text1.setText(CtrCode);
        text1.setEditable(false);
        text2.setText(newCtrCode);
        text3.setText(serviceCode);
        
        Color color=Color.red;
        for (int line : lines) {
            setLineForeground(text1, line, color);
        }

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        bt1.addActionListener(new ActionListener()//窗口监听
        {
            public void actionPerformed(ActionEvent e)//菜单项
            {
                // 获取按钮点击事件源
                JButton source = (JButton) e.getSource();

                // 获取按钮所在的顶层容器（即 JFrame）
                JFrame frame = (JFrame) SwingUtilities.getRoot(source);

                // 关闭窗口
                frame.dispose();
            }
        });
        
        bt2.addActionListener(new ActionListener()//窗口监听
        {
            public void actionPerformed(ActionEvent e)//菜单项
            {
            	try {
                    String parentPath = ft.extractParentDirectory(filePath);
                    String fileName = ft.extractFileName(filePath);
                    String extractServiceName = ft.genExtractServiceName(parentPath, fileName);
                    String txtController = ft.genTxtControllerName(parentPath, fileName);
                    final String newModCtrCode = text2.getText();
                    final String newServiceCode = text3.getText();
                    ft.writeFile(extractServiceName, newServiceCode);
                    ft.writeFile(txtController, CtrCode);
                    ft.writeFile(filePath, newModCtrCode);
                    
                    
                    // 获取按钮点击事件源
                    JButton source = (JButton) e.getSource();

                    // 获取按钮所在的顶层容器（即 JFrame）
                    JFrame frame = (JFrame) SwingUtilities.getRoot(source);

                    // 关闭窗口
                    frame.dispose();
                    
                    JOptionPane.showMessageDialog(null, "应用重构成功！", "Service角色缺失异味重构", JOptionPane.INFORMATION_MESSAGE);

            	}
            	catch (Exception e1) {
                    JOptionPane.showMessageDialog(null, "应用重构失败！", "Service角色缺失异味重构", JOptionPane.INFORMATION_MESSAGE);
                    e1.printStackTrace();
            	}
            }
        });
    }
    
    public void setLineForeground(JTextPane textPane, int line, Color color) {
        StyledDocument doc = textPane.getStyledDocument();
        Element root = doc.getDefaultRootElement();

        int startOffset = root.getElement(line - 1).getStartOffset();
        int endOffset = root.getElement(line - 1).getEndOffset() - 1;

        Style style = textPane.addStyle("LineHighlight", null);
        StyleConstants.setForeground(style, color);

        doc.setCharacterAttributes(startOffset, endOffset - startOffset, textPane.getStyle("LineHighlight"), true);
    }
}
