package com.mrliu.util;
import com.sun.jna.Library;
import com.sun.jna.Native;
import org.apache.log4j.Logger;

import java.text.SimpleDateFormat;
import java.util.Date;

public class PrintUtil {
    private static Logger log = Logger.getLogger(PrintUtil.class);

    public interface nativeDLL extends Library {
        nativeDLL INSTANCE = (nativeDLL) Native.loadLibrary("D:\\hx\\PrinterDLL.dll", nativeDLL.class);

        public int Printer_Port_Open(String strPortName, Object lBaudRate, int lPortTyp);// 打开端口

        public boolean Printer_Set_Reset(int hPrinterID);// 还原打印机默认状态

        public int Printer_Port_Close(int lPortTyp);// 关闭端口

        public boolean Printer_Output_String(int hPrinterID, String print_content); // 打印字符串空字符串结尾

        public boolean Printer_Control_BlackMark(int hPrinterID);

        public boolean Printer_Set_Encoding(int hPrinterID, int eNcoding);// 设置编码方式

        public boolean Printer_Output_Data(int hPrinterID, String print_content, int length); // 打印字符串

        public boolean Printer_Control_FeedLines(int hPrinterID, int iDotLines); // 向前进纸张

        public boolean Printer_Control_CutPaper(int hPrinterID, int iType, int iLines);// 打印机裁纸

        public boolean Printer_Set_FontSize(int hPrinterID, int iWidth, int iHeight);// 设置打印字体大小
        // hPrinterID：打印机句柄，由打开端口的返回值确定
        // iWidth:
        // 字体宽度大小，1=正常，2-7=放大倍数
        // iHeight:
        // 字体高度大小，1=正常，2-7=放大倍数

        public boolean Printer_Set_FontBold(int hPrinterID, Boolean bBold);// 设置打印字体是否位粗体
        // hPrinterID：打印机句柄，由打开端口的返回值确定
        // bBold:
        // TRUE=粗体，FALSE=正常

        public boolean Printer_Set_AlignType(int hPrinterID, int lAlignType);// 设置对齐方式类型，0=左对齐,
        // 1=居中对齐,
        // 2=右对齐

    }

    // 打开打印机端口
    public static Integer print_open() {
        int handId = nativeDLL.INSTANCE.Printer_Port_Open("LPT1", null, 1);
        return handId;
    }

    // 打印排队号
    public static void print_munber(int hand_id, String number, int waiter, String xm, String ywlx,String sfzmhm) throws InterruptedException {

        if(number.length()==1) {
            number="000"+number;
        }else if(number.length()==2){
            number="00"+number;
        }else if(number.length()==3) {
            number="0"+number;
        }else {
            //最后三位
            System.out.println("打印-----异常");
        }
        int time = 240;
        System.out.println("进入打印队列==打印机句柄：" + hand_id);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        System.out.println(sdf.format(new Date()));
        boolean ret = false;
        ret = nativeDLL.INSTANCE.Printer_Set_AlignType(hand_id, 0);
        Thread.sleep(time);
        ret = nativeDLL.INSTANCE.Printer_Set_FontSize(hand_id, 1, 1);
        Thread.sleep(time);
        ret = nativeDLL.INSTANCE.Printer_Output_String(hand_id, "尊敬的客户:"+xm+sfzmhm+"\n");
        Thread.sleep(time);
        ret = nativeDLL.INSTANCE.Printer_Set_FontSize(hand_id, 2, 1);
        Thread.sleep(time);
        ret = nativeDLL.INSTANCE.Printer_Set_AlignType(hand_id, 1);
        Thread.sleep(time);
        ret = nativeDLL.INSTANCE.Printer_Output_String(hand_id, "太和县不动产登记中心\n");
        Thread.sleep(time);
        ret = nativeDLL.INSTANCE.Printer_Set_FontSize(hand_id, 4, 4);
        Thread.sleep(time);
        ret = nativeDLL.INSTANCE.Printer_Set_FontBold(hand_id, true);
        Thread.sleep(time);
        ret = nativeDLL.INSTANCE.Printer_Set_AlignType(hand_id, 1);
        Thread.sleep(time);
        ret = nativeDLL.INSTANCE.Printer_Output_String(hand_id, number + "\n");// 参数一
        Thread.sleep(time);
        ret = nativeDLL.INSTANCE.Printer_Set_FontSize(hand_id, 1, 1);
        Thread.sleep(time);
        ret = nativeDLL.INSTANCE.Printer_Set_FontBold(hand_id, false);
        if("1".equals(ywlx)){
            Thread.sleep(time);
            ret = nativeDLL.INSTANCE.Printer_Output_String(hand_id,"不动产登记\n");
        }else if("02".equals(ywlx)){
            Thread.sleep(time);
            ret = nativeDLL.INSTANCE.Printer_Output_String(hand_id,"公司综合业务\n");
        }else{
            Thread.sleep(time);
            ret = nativeDLL.INSTANCE.Printer_Output_String(hand_id,"VIP综合业务\n");
        }

        Thread.sleep(time);
        ret = nativeDLL.INSTANCE.Printer_Output_String(hand_id,
                "您的前面共有 " + waiter
                        + " 人等待！                        \n 注意显示屏信息，过号无效，请您重新取号          "
                        + sdf.format(new Date()) + "\n"); // 参数二

        boolean pcf = nativeDLL.INSTANCE.Printer_Control_CutPaper(hand_id, 1, 260);
        Thread.sleep(time);
    }

	/*public static void main(String[] args) throws InterruptedException {
		int kk = print_open();

			print_munber(kk, "0001", 2);
		System.exit(0);

		nativeDLL.INSTANCE.Printer_Port_Close(kk);
	}*/
}
