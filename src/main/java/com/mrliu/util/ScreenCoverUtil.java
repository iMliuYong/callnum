package com.mrliu.util;/**
 * Created by iMliu  on  2019/8/30
 */

/**
 *
 *@description: TODO
 *
 *@author : iMliu
 *@date  : 2019/8/30 9:00
 */
public class ScreenCoverUtil {

    //获取配置文件
    private static PropertiesLoader loader = new PropertiesLoader("application.properties");


    public static String CoverScreen(String para) {// 窗口显示屏字符工具类
        String res ="";
        if(para.length()==1) {
            res = para+"2";
        }else if(para.length()==2) {
            res = para.split("")[0]+"2"+para.split("")[1]+"2";
        }else if(para.length()==3) {
            res = para.split("")[0]+"2"+para.split("")[1]+"2"+para.split("")[2]+"2";
        }else if(para.length()==4) {
            res = para.split("")[0]+"2"+para.split("")[1]+"2"+para.split("")[2]+"2"+para.split("")[3]+"2"+para.split("")[4]+"2";
        }else {
            res = para.split("")[0]+"2"+para.split("")[1]+"2"+para.split("")[2]+"2"+para.split("")[3]+"2"+para.split("")[4]+"2"+para.split("")[5]+"2";
        }
        return res;
    }

    public static String CoverPdh(String para) {// 六合一排队号工具类
        String res ="";
        if(para.length()==1) {
            res ="0000"+para;
        }else if(para.length()==2) {
            res ="000"+para;
        }else if(para.length()==3) {
            res ="00"+para;
        }else  if(para.length()==4){
            res ="0"+para;
        }else {
            res = para;
        }
        return res;
    }
}
