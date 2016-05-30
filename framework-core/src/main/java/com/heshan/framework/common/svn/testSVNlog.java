package com.heshan.framework.common.svn;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.LineNumberReader;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class testSVNlog {
	public static String patchFile="F:/result";//svn打出的日志文件
    
    public static String projectPath="E:/workspace_0512/tiyan_cookies";//工作空间路径  
      
    //public static String webContent="WebContent";//web应用文件夹名  
      
    //public static String classPath="E:/work_mtOrder/tiyan_mtorder/jzbpp-order/target/classes";//class存放路径  
    
    //public static String staticPath="E:/work_mtOrder/tiyan_mtorder/jzbpp-order";//静态文件存放路径  
    
    public static String desPath="E:/realese20150810";//增量文件保存路径
    
    public static String svnPath="/branches/tiyan_jzerp";//svn上分支路径
      
      
    /** 
     * @param args 
     * @throws Exception  
     */  
    public static void main(String[] args) throws Exception {  
        copyFiles(getPatchFileList()); 
    	//getPatchFileList();
    }  
      
    public static List<String> getPatchFileList() throws Exception{  
        List<String> fileList=new ArrayList<String>();  
        FileInputStream f = new FileInputStream(patchFile);   
        BufferedReader dr=new BufferedReader(new InputStreamReader(f));  
        String line; 
        while((line=dr.readLine())!=null){
        	if(!"".equals(line)){
            	String lines[]=line.split(" ");
            	if("".equals(lines[0])){
            		if(!lines[4].replaceAll(svnPath, "").equals("")){
                        fileList.add(lines[4].replaceAll(svnPath, ""));
                      }
            	}

        	}
        }   
        return fileList;  
    }  
      
    public static void copyFiles(List<String> list){  
        int number=0; 
        for(String fullFileName:list){
        	String[] fullFileNames=fullFileName.split("/"); 
        	// /jzbpp-contract/src/main/java/cn/bidlink/junzheng/contract/common/ContractMaintenanceConstants.java
        	//采用 /进行分组 取数组第一个参数 进行判断 区分出来是dao services 还是web层
        	String regEx = "-dao|-service"; // 表示@或_
        	Pattern p = Pattern.compile(regEx);
        	Matcher m = p.matcher(fullFileNames[1]);
        	boolean rs = m.find();
	        if(fullFileName.indexOf("src/main/java")!=-1){//对源文件目录下的文件处理  
	            String fileName=fullFileName;  
	            //E:/work_mtOrder/tiyan_mtorder/jzbpp-contract/target/classes/cn/bidlink/junzheng/contract/common/ContractMaintenanceConstants.java
	            fullFileName=projectPath+fullFileName.replace("src/main/java","target/classes");  
	            if(fileName.endsWith(".java")){  
	                fileName=fileName.replace(".java",".class");  
	                fullFileName=fullFileName.replace(".java",".class");  
	            }
	            //E:/work_mtOrder/tiyan_mtorder/jzbpp-contract/src/main/java/cn/bidlink/junzheng/contract/common
	            String tempDesPath=fileName.substring(0,fileName.lastIndexOf("/"));
	            //E:/realese/jzbpp-contract/WEB-INF/classes/cn/bidlink/junzheng/contract/common
	            String desFilePathStr=desPath+tempDesPath.replaceAll("src/main/java", isWebProject(!rs));
	            //E:/realese/jzbpp-contract/WEB-INF/classes/cn/bidlink/junzheng/contract/common/ContractMaintenanceConstants.class
	            String desFileNameStr=desPath+fileName.replaceAll("src/main/java", isWebProject(!rs));  
	            File desFilePath=new File(desFilePathStr);  
	            if(!desFilePath.exists()){  
	                desFilePath.mkdirs();  
	            }  
	            copyFile(fullFileName, desFileNameStr);
	            number++;
	            System.out.println(fullFileName+"复制完成(class文件)");  
	        }else if(fullFileName.indexOf("src/main/webapp")!=-1){//对静态页面的处理  
	        	// /jzbpp-order/src/main/webapp/WEB-INF/pages/order/order/goodDetalis.jsp
	        	// /jzbpp-contract/src/main/webapp/WEB-INF/pages/conTemplate
	        	String fileName=fullFileName;  
	            //E:/work_mtOrder/tiyan_mtorder/jzbpp-order/src/main/webapp/WEB-INF/pages/order/order/goodDetalis.jsp
	        	 if(!fileName.endsWith(".jsp")&&!fileName.endsWith(".js")&&!fileName.endsWith(".css")&&!fileName.endsWith(".xml")){  
		              continue; //处理新增目录的情况
		         }
	            fullFileName=projectPath+fullFileName;  
	            // /jzbpp-order/src/main/webapp/WEB-INF/pages/order/order
	            String tempDesPath=fileName.substring(0,fileName.lastIndexOf("/"));
	            //E:/realese//jzbpp-order/WEB-INF/pages/order/order
	            String desFilePathStr=desPath+tempDesPath.replaceAll("src/main/webapp","");
	            //E:/realese//jzbpp-order/WEB-INF/pages/order/order/goodDetalis.jsp
	            String desFileNameStr=desPath+fileName.replaceAll("src/main/webapp","");  
	            File desFilePath=new File(desFilePathStr);  
	            if(!desFilePath.exists()){  
	                desFilePath.mkdirs();  
	            }  
	            copyFile(fullFileName, desFileNameStr); 
	            number++;
	            System.out.println(fullFileName+"复制完成(静态文件)");
	        }       
        }  
        
        System.out.println("共处理了"+number+"个文件");
          
    }  
  
    private static void copyFile(String sourceFileNameStr, String desFileNameStr) {  
        File srcFile=new File(sourceFileNameStr);  
        File desFile=new File(desFileNameStr);  
        try {  
            copyFile(srcFile, desFile);  
        } catch (IOException e) {  
            e.printStackTrace();  
        }  
    }
    //判断项目是否为web项目
    private static String isWebProject(Boolean flag) {  
       String str="";
       if(flag){
    	   str="WEB-INF/classes";
       }
       return str;
    }    
    public static int getLineNumbers(String file){
    	File test= new File(file); 
    	long fileLength = test.length(); 
    	LineNumberReader rf = null; 
    	int lines = 0;
    	try { 
    	   rf = new LineNumberReader(new FileReader(test)); 
    	if (rf != null) { 
    	    rf.skip(fileLength); 
    	    lines = rf.getLineNumber(); 
    	    rf.close(); 
    	 } 
    	} catch (IOException e) { 
    	   if (rf != null) { 
    	     try { 
    	           rf.close(); 
    	         } catch (IOException ee) { 
    	      } 
    	  } 
    	}
    	return lines;
    }
      
      
    public static void copyFile(File sourceFile, File targetFile) throws IOException {  
        BufferedInputStream inBuff = null;  
        BufferedOutputStream outBuff = null;  
        try {  
            // 新建文件输入流并对它进行缓冲  
            inBuff = new BufferedInputStream(new FileInputStream(sourceFile));  
  
            // 新建文件输出流并对它进行缓冲  
            outBuff = new BufferedOutputStream(new FileOutputStream(targetFile));  
  
            // 缓冲数组  
            byte[] b = new byte[1024 * 5];  
            int len;  
            while ((len = inBuff.read(b)) != -1) {  
                outBuff.write(b, 0, len);  
            }  
            // 刷新此缓冲的输出流  
            outBuff.flush();  
        } finally {  
            // 关闭流  
            if (inBuff != null)  
                inBuff.close();  
            if (outBuff != null)  
                outBuff.close();  
        }  
    }  
      
}
