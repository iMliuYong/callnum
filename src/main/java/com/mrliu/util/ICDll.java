package com.mrliu.util;

import com.sun.jna.Library;
import com.sun.jna.Native;
import com.sun.jna.ptr.IntByReference;

/***
 * 身份证识别器类库(采用JNA开发)
 * @author zdw
 *
 */
public interface ICDll extends Library
{
    ICDll instance = (ICDll)Native.loadLibrary("D:\\hx\\termb.dll", ICDll.class);

    /***
     * 打开端口
     * @param port 1001～1016(十进制)为 USB 口。 例如:1001： USB1,1002,USB2
     * @return
     */
    public int CVR_InitComm(int port);//初始化连接

    public int CVR_SelectCard();//选卡

    public  int CVR_FindCard();//找卡

    public  int CVR_Authenticate();//卡认证

    public int CVR_Read_Content(int active);//读卡操作

    public  int CVR_CloseComm(); //关闭连接

    public int Getbase64JpgData(byte[] data, IntByReference i);//获取身份证照片

    public int GetPeopleNameU(byte[] data, IntByReference i);//获取姓名

    public int GetPeopleIDCode(byte[] data,IntByReference i);//获取身份证号码

}
