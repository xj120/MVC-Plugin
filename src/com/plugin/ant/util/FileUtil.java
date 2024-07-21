package com.plugin.ant.util;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import com.mvcRstr.model.CodeR.BlockLine;
import com.plugin.ant.model.SmellClass;
import com.plugin.ant.model.SmellType;

import br.com.aniche.ck.FileUtils;
import mid_al.backtrack.test;

public class FileUtil {
	
	public String getJavaPath(String projectPath, String className) {
		String[] javaFiles = FileUtils.getAllJavaFiles(projectPath);
		String cName = className.replace(".", "\\");
		for(String path:javaFiles) {
			if(path.contains(cName)) {
				return path;
			}
		}
		return null;
	}
	
	
    public String readJavaFile(String javaPath) throws IOException {
        StringBuilder codeText=new StringBuilder();
        File myFile = new File(javaPath);
        if (myFile.isFile() && myFile.exists()) { //判断文件是否存在
            InputStreamReader Reader = new InputStreamReader(new FileInputStream(myFile), "UTF-8");
            //考虑到编码格式，new FileInputStream(myFile)文件字节输入流，以字节为单位对文件中的数据进行读取
            //new InputStreamReader(FileInputStream a, "编码类型")
            //将文件字节输入流转换为文件字符输入流并给定编码格式

            BufferedReader bufferedReader = new BufferedReader(Reader);
            //BufferedReader从字符输入流中读取文本，缓冲各个字符，从而实现字符、数组和行的高效读取。

            String lineTxt = null;

            while ((lineTxt = bufferedReader.readLine()) != null) {
                //buffereReader.readLine()按行读取写成字符串
                codeText.append(lineTxt+"\n");
//                System.out.println(lineTxt);
            }

            Reader.close();

        } else {
            System.out.println("找不到指定的文件");
        }
        return codeText.toString();
    }
    
    
    public List<Integer> setToList(List<BlockLine> statem_Lines){
    	List<Integer> lines=new ArrayList<Integer>();
    	for(BlockLine block:statem_Lines) {
    		Set<Integer> set = block.getBlock();
    		List<Integer> temp=new ArrayList<Integer>(set);
    		for(int line:temp) {
    			lines.add(line);
    		}
    	}
    	return lines;
    }
    
    
    public void deleteByAllName(List<SmellClass> classes, String allName) {
    	
    }
    
    
    //提取父目录
    public String extractParentDirectory(String path) {
        File file = new File(path);
        String parentDirectory = file.getParent();
        return parentDirectory;
    }
    
    
    //提取文件名
    public String extractFileName(String filePath) {
        // 创建 File 对象
        File file = new File(filePath);

        // 使用 File 对象的 getName() 方法获取文件名
        String fileName = file.getName();
        
        int lastDotIndex = fileName.lastIndexOf(".");
        if(lastDotIndex != -1) {
        	return fileName.substring(0, lastDotIndex);
        }
        else {
        	return fileName;
        }

    }
    
    
    //新生成service类名
    public String genExtractServiceName(String parentPath, String fileName) {
    	return parentPath + "\\" + fileName + "_Service.java";
    }
    
    
    //Controller副本
    public String genTxtControllerName(String parentPath, String fileName) {
    	return parentPath + "\\" + fileName + ".txt";
    }
    
    
    //生成文件
	public void writeFile(String path, String text) {
		try (BufferedWriter writer = new BufferedWriter(new FileWriter(path))) {
            writer.write(text);
            System.out.println("数据已成功写入到文件：" + path);
        } catch (IOException e) {
            System.err.println("写入文件时出错：" + e.getMessage());
        }
	}
	
	
	//相关重构代码添加注释
	public String addAnnotation(String ctrCode, List<BlockLine> list) throws Exception{
		int count = 0;
		ArrayList<Integer> changeLines = new ArrayList<Integer>();
		
		for(BlockLine blockLine : list) {
			int[] range = blockLine.get_blockRange();
			int start = range[0] + count * 3 + 1;
			int end = range[1] + count * 3 + 2;
			changeLines.add(start);
			changeLines.add(end);
			count++;
		}
		
		ByteArrayInputStream inputStream = new ByteArrayInputStream(ctrCode.getBytes());
		InputStreamReader read = new InputStreamReader(inputStream);
		BufferedReader bufferReader = new BufferedReader(read);
		
		int line = 0;
		String lineText = null;
		
		String modifiedCtrCode = "";
		
		while((lineText = bufferReader.readLine())!=null) {
			line++;
			modifiedCtrCode += lineText+"\n";
			if(changeLines.contains(line)) {
				int index = changeLines.indexOf(line);
				if(index % 2 == 0) {
					modifiedCtrCode+="/*\n";
				}
				else {
					modifiedCtrCode+="*/\n";
				}
			}
		}
		
		return modifiedCtrCode;
	}
}
