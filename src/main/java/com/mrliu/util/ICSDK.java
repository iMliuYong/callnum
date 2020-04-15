package com.mrliu.util;/**
 * Created by iMliu  on  2019/8/28
 */

import com.sun.jna.ptr.IntByReference;

import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;

/**
 *
 *@description: TODO
 *
 *@author : iMliu
 *@date  : 2019/8/28 9:55
 */
public class ICSDK {
    //获取配置文件
    private static PropertiesLoader loader = new PropertiesLoader("application.properties");
    public ICSDK()
    {
        int sdtport =Integer.parseInt(loader.getProperty("SDTPort")) ;

        //初始化连接
        int result = ICDll.instance.CVR_InitComm(sdtport);
        System.out.println(Integer.toHexString(result));

        //卡认证
        result  = ICDll.instance.CVR_Authenticate();
        System.out.println(Integer.toHexString(result));
        result = ICDll.instance.CVR_Read_Content(4);

        System.out.println(Integer.toHexString(result));
    }

    public static ICSDK getInstance()
    {
        return new ICSDK();
    }


    /**
     * 获取姓名
     * @return
     */
    public String GetPeopleName() throws UnsupportedEncodingException {
        byte[] data = new byte[20];
        IntByReference count = new IntByReference();
        int res = ICDll.instance.GetPeopleNameU(data,count);
        return new String(data,"UTF_16LE");
    }

    /**
     * 获取身份证号
     * @return
     */
    public String GetSfzmhm(){
        byte[] data = new byte[40];
        IntByReference count = new IntByReference();
        int res = ICDll.instance.GetPeopleIDCode(data,count);
        return new String(data,StandardCharsets.UTF_8);
    }

    /**
     * 获取身份证照片信息 base64
     * @return
     */
    public String GetSfzmzp(){
        byte[] data = new byte[1024*24];
        IntByReference count = new IntByReference();
        int res = ICDll.instance.Getbase64JpgData(data,count);
        return new String(data,StandardCharsets.UTF_8);
    }
}
