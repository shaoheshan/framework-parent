package com.heshan.framework.common.file;

import java.io.File;

/**
 * @author <a href="mailto:heshanshao@ebnew.com">heshanshao</a>
 * @version V1.0
 * @description
 * @date 2016/5/11 16:55
 */
public class FileIterator {

    private  final  static  String c="C:\\Users\\Administrator\\AppData\\Roaming\\NetSarang\\Xshell\\Sessions\\正式应用";
    public static void main(String[] args) {
        File file=new File(c);
        if (file.isDirectory()){

            File[] file1= file.listFiles();
            for (int i = 0; i <file1.length ; i++) {
                if (file1[i].getName().contains("xsh")){
                    System.out.println(file1[i].getName().substring(0,file1[i].getName().length()-5).replace("(","      "));
                }

            }

        }

    }
}
