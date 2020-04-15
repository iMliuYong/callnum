package com.mrliu.util;/**
 * Created by iMliu  on  2019/8/30
 */

import com.arcsoft.face.FaceEngine;

import java.util.ArrayList;
import java.util.List;

/**
 *
 *@description: 存储一个客户号码和所有等待服务的客户号码的队列集合
 *
 *@author : iMliu
 *@date  : 2019/8/30 9:17
 */
public class NumberManager {
    private int lastNumber = 0;
    private int printer = 0;
    private String queueWinWaitNumber = "";
    private List queueNumbers = new ArrayList();		//叫号等待人数
    private List queueWindowWaits = new ArrayList();	//窗口等待人数

    public String generateWindow(String reqdata) {
        queueWindowWaits.add(reqdata);
        return queueWinWaitNumber;
    }

    public synchronized String fetchWinNumber() {//窗口呼叫等待人数
        if(queueWindowWaits.size()>0) {
            return  (String)queueWindowWaits.remove(0);
        }else {
            return null;
        }
    }

    public synchronized String  generateNewNumber(String sfzmhm,String ywlb) {//生成号
        String number = String.valueOf(++lastNumber)+"-"+sfzmhm+"-"+ywlb;
        queueNumbers.add(number);
        return number;
    }

    public synchronized Integer getNowNumber() {//获取当前等待人数
        if(queueNumbers.size()>0) {
            return (Integer) queueNumbers.size();
        }else {
            return 0;
        }
    }

    public synchronized Integer getWinWaitNumber() {//获取窗口等待数
        if(queueWindowWaits.size()>0) {
            return (Integer) queueWindowWaits.size();
        }else {
            return 0;
        }
    }

    public synchronized String fetchNumber() {//叫号
        if(queueNumbers.size()>0) {
            return (String) queueNumbers.remove(0);
        }else {
            return null;
        }
    }

    //打开打印端口
    public synchronized Integer generatePrinter(int print) {//生成号
        printer = print;
        return printer;
    }

    public synchronized Integer getPrinterNum() {
        return printer;
    }

   /* //初始化摄像头
    public  synchronized FaceEngine generateFaceEngine(FaceEngine faceEngine){
        faceEngines = faceEngine;
        return faceEngines;
    }

    public synchronized  FaceEngine getFaceEngines(){
        return faceEngines;
    }*/
}
