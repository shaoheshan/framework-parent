package com.heshan.framework.common.string;

/**
 * @author <a href="mailto:heshanshao@ebnew.com">heshanshao</a>
 * @version V1.0
 * @description  过滤出一个字符串中的数字转成整数
 *  两种方式 1) 正则进行替换 2) 通过for循环字符 判断每个字符是否为数字
 * @date 2016/5/11 14:33
 */
public class FilterNumberDemo {
    public static void main(String[] args) {

        String str="123bac456sadd11##1";
        //str = str.replaceAll("[^(0-9)]", "");
       // System.out.println(Integer.valueOf(str));
        StringBuilder str1=new StringBuilder();
        for (int i = 0; i <str.length() ; i++) {
            char c=str.charAt(i);
            if(Character.isDigit(c)) {
                    str1.append(c);
            }

        }
        System.out.println(Integer.valueOf(str1.toString()));

    }
}
