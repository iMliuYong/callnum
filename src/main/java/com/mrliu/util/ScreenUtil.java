package com.mrliu.util;/**
 * Created by iMliu  on  2019/9/5
 */

import com.sun.jna.Library;
import com.sun.jna.Native;

/**
 *
 *@description: TODO
 *
 *@author : iMliu
 *@date  : 2019/9/5 17:22
 */
public class ScreenUtil {
    public interface nativeDLL extends Library {

        ///当前路径是在项目下，而不是bin输出目录下。
        nativeDLL INSTANCE = (nativeDLL) Native.loadLibrary(
                        "D:\\hx\\PD101Ctrl_RZC2.dll",
                        nativeDLL.class);

        public  void pd101a_rzc2_Open(int nComPort, int nBaudrate);
        public  void pd101a_rzc2_SendText(int nCardId, String sss);
        public  void pd101a_rzc2_Close();
    }

    public static synchronized void Call_screen(String winNum,String call_num,String lb) {
        nativeDLL.INSTANCE.pd101a_rzc2_Open(1,9600);//串口1，波特率9600
        System.out.println("打开串口成功！");
        if("Suspend".equals(lb)){//82112711
            nativeDLL.INSTANCE.pd101a_rzc2_SendText(Integer.parseInt(winNum), "暂2停2受2理2");
        }else if("Recover".equals(lb)) {
            nativeDLL.INSTANCE.pd101a_rzc2_SendText(Integer.parseInt(winNum), "正2在2受2理2");
        }else {
            if(call_num.length()==1) {
                nativeDLL.INSTANCE.pd101a_rzc2_SendText(Integer.parseInt(winNum),"请2020202"+call_num+"2号2,2到2"+ScreenCoverUtil.CoverScreen(winNum)+"号2窗2口2办2理2业2务2");
                nativeDLL.INSTANCE.pd101a_rzc2_SendText(128,"020202"+call_num+"2号2到2"+ScreenCoverUtil.CoverScreen(winNum)+"号2窗2口2");
                nativeDLL.INSTANCE.pd101a_rzc2_SendText(129,"020202"+call_num+"2号2到2"+ScreenCoverUtil.CoverScreen(winNum)+"号2窗2口2");
            }else if(call_num.length()==2) {
                nativeDLL.INSTANCE.pd101a_rzc2_SendText(Integer.parseInt(winNum),"请20202"+call_num.split("")[0]+"2"+call_num.split("")[1]+"2号2,2到2"+ScreenCoverUtil.CoverScreen(winNum)+"号2窗2口2办2理2业2务2");
                nativeDLL.INSTANCE.pd101a_rzc2_SendText(128,"0202"+call_num.split("")[0]+"2"+call_num.split("")[1]+"2号2到2"+winNum.split("")[0]+"2"+winNum.split("")[1]+"2号2窗2口2");
                nativeDLL.INSTANCE.pd101a_rzc2_SendText(129,"0202"+call_num.split("")[0]+"2"+call_num.split("")[1]+"2号2到2"+winNum.split("")[0]+"2"+winNum.split("")[1]+"2号2窗2口2");
            }else if(call_num.length()==3) {
                nativeDLL.INSTANCE.pd101a_rzc2_SendText(Integer.parseInt(winNum),"请202"+call_num.split("")[0]+"2"+call_num.split("")[1]+"2"+call_num.split("")[2]+"2号2,2到2"+ScreenCoverUtil.CoverScreen(winNum)+"号2窗2口2办2理2业2务2");
                nativeDLL.INSTANCE.pd101a_rzc2_SendText(128,"02"+call_num.split("")[0]+"2"+call_num.split("")[1]+"2"+call_num.split("")[2]+"2号2,2到2"+winNum+"2号2窗2口2");
                nativeDLL.INSTANCE.pd101a_rzc2_SendText(129,"02"+call_num.split("")[0]+"2"+call_num.split("")[1]+"2"+call_num.split("")[2]+"2号2,2到2"+winNum+"2号2窗2口2");
            }else if(call_num.length()==4) {
                nativeDLL.INSTANCE.pd101a_rzc2_SendText(Integer.parseInt(winNum),"请2"+call_num.split("")[0]+"2"+call_num.split("")[1]+"2"+call_num.split("")[2]+"2"+call_num.split("")[3]+"2号2,2到2"+ScreenCoverUtil.CoverScreen(winNum)+"号2窗2口2办2理2业2务2");
                nativeDLL.INSTANCE.pd101a_rzc2_SendText(128,call_num.split("")[0]+"2"+call_num.split("")[1]+"2"+call_num.split("")[2]+"2"+call_num.split("")[3]+"2号2,2到2"+ScreenCoverUtil.CoverScreen(winNum)+"号2窗2口2");
                nativeDLL.INSTANCE.pd101a_rzc2_SendText(129,call_num.split("")[0]+"2"+call_num.split("")[1]+"2"+call_num.split("")[2]+"2"+call_num.split("")[3]+"2号2,2到2"+ScreenCoverUtil.CoverScreen(winNum)+"号2窗2口2");
            }else {
                nativeDLL.INSTANCE.pd101a_rzc2_SendText(Integer.parseInt(winNum),"请2"+call_num.split("")[0]+"2"+call_num.split("")[1]+"2"+call_num.split("")[2]+"2"+call_num.split("")[3]+"2"+call_num.split("")[4]+"2号2,2到2"+ScreenCoverUtil.CoverScreen(winNum)+"号2窗2口2办2理2业2务2");
                nativeDLL.INSTANCE.pd101a_rzc2_SendText(128,call_num.split("")[0]+"2"+call_num.split("")[1]+"2"+call_num.split("")[2]+"2"+call_num.split("")[3]+"2"+call_num.split("")[4]+"2号2,2到2"+ScreenCoverUtil.CoverScreen(winNum)+"号2窗2口2");
                nativeDLL.INSTANCE.pd101a_rzc2_SendText(129,call_num.split("")[0]+"2"+call_num.split("")[1]+"2"+call_num.split("")[2]+"2"+call_num.split("")[3]+"2"+call_num.split("")[4]+"2号2,2到2"+ScreenCoverUtil.CoverScreen(winNum)+"号2窗2口2");
            }
        }
    }
    public static synchronized void GZL_screen(String winNum,String gzl) {
        if(!"".equals(gzl)) {
            nativeDLL.INSTANCE.pd101a_rzc2_SendText(126,"窗2口2"+ScreenCoverUtil.CoverScreen(winNum)+"工2作2量2:2"+ScreenCoverUtil.CoverScreen(gzl)+"");
        }
    }
}
