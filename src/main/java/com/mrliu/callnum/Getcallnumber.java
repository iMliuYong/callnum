package com.mrliu.callnum;/**
 * Created by iMliu  on  2019/8/29
 */

import com.google.gson.Gson;
import com.mrliu.bean.Callnum;
import com.mrliu.bean.JhReponse;
import com.mrliu.bean.Jhcallout;
import com.mrliu.service.CalloutService;
import com.mrliu.util.*;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Properties;

/**
 *
 *@description: 取号
 *
 *@author : iMliu
 *@date  : 2019/8/29 19:23
 */

@Controller
@RequestMapping(value = "/GetCallnumber")
public class Getcallnumber {
    //配置 日志
    private static final Logger log = Logger.getLogger(Getcallnumber.class);

    //获取配置文件
    private static PropertiesLoader loader = new PropertiesLoader("application.properties");

    @Autowired
    private CalloutService calloutservice;


    /**
     * 功能描述: 叫号
     *
     * @param:
     * @return:
     * @auther: iMliu
     * @date: 2019/9/6 18:27
     */
    @RequestMapping(path = {"/callout"})
    @ResponseBody
    public void queueNumber(HttpServletRequest req,HttpServletResponse resp){
        resp.setCharacterEncoding("UTF-8");
        resp.setContentType("application/json; charset=utf-8");
        PrintWriter pw =null;
        try {
            pw = resp.getWriter();
            String winnum = req.getParameter("winNum");
            log.info("------------- "+winnum+"叫号 ---------------");
            List<JhReponse> list = new ArrayList<JhReponse>();
            JhReponse res = new JhReponse();
            Jhcallout jhcallout = new Jhcallout();
            Gson gson = new Gson();
            String type = "jh";
            jhcallout = calloutservice.getQueueNum(winnum,type);
            if(jhcallout!=null) {
                ScreenUtil.Call_screen(winnum, jhcallout.getQhxxxlh().substring(8),"");//窗口屏显示
                SpeakUtil.Call_Waiter(winnum, jhcallout.getQhxxxlh().substring(8));
                log.info("请"+jhcallout.getQhxxxlh().substring(8)+"号,到"+winnum+"窗口办理业务");
                res.setRespCode("200");
                res.setRespMsg("叫号成功");
                res.setRespData(jhcallout);
                list.add(res);
                pw.print(gson.toJson(list));
                pw.flush();
            }else {
                System.out.println("没有等待客户！！！");//没有等待用户，往窗口队列写窗口等待人数
                res.setRespCode("400");
                res.setRespMsg("没有待办客户");
                res.setRespData(jhcallout);
                list.add(res);

                pw.print(gson.toJson(list));
                pw.flush();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            if(pw!=null){
                pw.close();
            }
        }
    }

    /**
     * 功能描述: 重呼
     *
     * @param:
     * @return:
     * @auther: iMliu
     * @date: 2019/9/6 18:28
     */
    @RequestMapping(path = {"/recall"})
    @ResponseBody
    public void recallNumber(HttpServletRequest req,HttpServletResponse resp){
        resp.setCharacterEncoding("UTF-8");
        resp.setContentType("application/json; charset=utf-8");
        PrintWriter pw =null;
        try {
            pw = resp.getWriter();
            String winnum = req.getParameter("winNum");
            log.info("------------- "+winnum+"重呼 ---------------");
            List<JhReponse> list = new ArrayList<JhReponse>();
            JhReponse res = new JhReponse();
            Gson gson = new Gson();
            List<Callnum> lists  = calloutservice.getRecallNum(winnum);
            if(lists!=null&&!lists.isEmpty()) {
                ScreenUtil.Call_screen(winnum, lists.get(0).getQhxxxlh().substring(8),"");//窗口屏显示
                SpeakUtil.Call_Waiter(winnum,lists.get(0).getQhxxxlh().substring(8));
                log.info("请"+lists.get(0).getQhxxxlh().substring(8)+"号,到"+winnum+"窗口办理业务");
                res.setRespCode("200");
                res.setRespMsg("重呼成功");
                list.add(res);
                pw.print(gson.toJson(list));
                pw.flush();
            }else {
                System.out.println("没有等待客户！！！");//没有等待用户，往窗口队列写窗口等待人数
                res.setRespCode("400");
                res.setRespMsg("没有待办客户");
                list.add(res);

                pw.print(gson.toJson(list));
                pw.flush();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            if(pw!=null){
                pw.close();
            }
        }
    }




    /**
     * 功能描述: 取号
     *
     * @param:
     * @return:
     * @auther: iMliu
     * @date: 2019/9/6 18:28
     */
    @RequestMapping(path = {"/getNumber"})
    @ResponseBody
    public void getNumbers(HttpServletRequest req, HttpServletResponse resp){
        resp.setCharacterEncoding("UTF-8");
        resp.setContentType("application/json; charset=utf-8");

        PrintWriter out = null;
        try {
            out = resp.getWriter();
            String sfzmhm = req.getParameter("sfzmhm"); //获取身份证号码
            String xm =req.getParameter("qhrxm");  //获取姓名

            String ywlb = req.getParameter("ywlx");//代理人标记
            String dlr = req.getParameter("Dlr");//代理人标记
            String xzcs = loader.getProperty("skcs");//刷取限制次数


            SimpleDateFormat sdf = new SimpleDateFormat("yyMMdd");
            String call_date = sdf.format(new Date());

            String qhxxxlh="";//排队序列号
            String nowNum = calloutservice.getNowNum();//当前排队编号
            Integer waitNum = calloutservice.getWaitNum();//获取排队人数
            Integer sfzmhmNum = calloutservice.getSfzmhmNum(sfzmhm);//获取身份证号码次数
            Boolean skcs = calloutservice.getSkcs();//话取当天刷卡次数限制


            int newNowNum =0;
            String oldNum ="";

            if(nowNum!=null&&!"".equals(nowNum)) {
                newNowNum=Integer.parseInt(nowNum);
                int nextNum = newNowNum+1;
                qhxxxlh = call_date+ ScreenCoverUtil.CoverPdh(String.valueOf(nextNum));//生成排队号
            }else {
                oldNum = calloutservice.getoldNum();//当前排队编号
                if(oldNum!=null&&!"".equals(oldNum)) {
                    int num_oldnext = Integer.parseInt(oldNum)+1;
                    qhxxxlh = call_date+ScreenCoverUtil.CoverPdh(String.valueOf(num_oldnext));
                }else {
                    qhxxxlh = call_date+ScreenCoverUtil.CoverPdh("1");
                }
            }

            if(!"".equals(sfzmhm)&&sfzmhmNum<Integer.parseInt(xzcs)) {
                if(skcs) {
                    Callnum callout =  new Callnum();
                    callout.setQhxxxlh(qhxxxlh);
                    callout.setPdh(String.valueOf(waitNum));
                    callout.setSfzmhm(sfzmhm);
                    callout.setYwlb(ywlb);
                    if(dlr!=null&&!"".equals(dlr)) {
                        callout.setDlrsfzmhm(sfzmhm);
                        callout.setRylb("2");
                    }else {
                        callout.setDlrsfzmhm("");
                        callout.setRylb("1");
                    }
                    callout.setQhrxm(xm);
                    callout.setState("0");
                    boolean res_callout = calloutservice.insertCallout(callout);
                    log.info("取号操作="+res_callout);

                    System.out.println("排队人数:"+newNowNum);

                    //打印机句柄
                    int handid = NumberMachine.getInstance().getCommonManager().getPrinterNum();
                    log.info("从队列中获取打印机的句柄为:"+handid);
                    try {
                        System.out.println("打印参数："+qhxxxlh.substring(8)+waitNum+xm+ywlb);
                        PrintUtil.print_munber(handid,qhxxxlh.substring(8),waitNum,xm,ywlb,sfzmhm);
                    } catch (InterruptedException e) {
                        System.out.println("打印异常:"+e.getMessage());
                        e.printStackTrace();
                    }

                    out.print(String.valueOf(newNowNum));
                    out.flush();
                    out.close();
                }else {
                    // 新增叫号每日人数限制

                    out.print(String.valueOf(-3));
                    out.flush();
                    out.close();
                }
            }else if(sfzmhmNum>=Integer.parseInt(xzcs)) {
                out.print(String.valueOf(-2));
                out.flush();
                out.close();
            }else {
                out.print(String.valueOf(-1));
                out.flush();
                out.close();
            }

        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            if(out!=null){
                out.close();
            }
        }
    }


    @RequestMapping(path = {"/GetWaitNum"})
    @ResponseBody
    public void GetWaitNum(HttpServletRequest request,HttpServletResponse response){
        PrintWriter out = null;
        try {
            out = response.getWriter();
            int data = calloutservice.getWaitNum();//获取排队人数 状态为0
            out.print(data);
            out.flush();
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            if(out!=null){
                out.close();
            }
        }
    }

    @RequestMapping(path = {"/login"})
    @ResponseBody
    public void Login(HttpServletRequest req,HttpServletResponse resp){
        PrintWriter pw =null;
        try {
            pw = resp.getWriter();
            String username  = req.getParameter("username");
            String password = req.getParameter("password");

            if(username!=null&&!"".equals(username)&&!"".equals(password)&&password!=null){
                String pd = loader.getProperty(username);
                if(password.equals(pd)){
                    pw.print("1");
                    pw.flush();
                }
            }else{
                pw.print("0");
                pw.flush();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            if(pw!=null){
                pw.close();
            }
        }
    }

    @RequestMapping(path = {"/FaceOption"})
    @ResponseBody
    public void FaceOption(HttpServletRequest req,HttpServletResponse resp){
        System.out.println("进入人脸后台管理");
        String mrz = req.getParameter("face_op");
        String face = calloutservice.getFaceOption();
        System.out.println("当前人脸状态："+face+",请求结果："+mrz);

        if(face!=null && !"".equals(face)){
            if(!face.equals(mrz)){
                calloutservice.upFaceOption(mrz);
            }
            log.info("人脸参数更新");
        }else{
            log.info("人脸组件配置参数为空");
        }
    }

    /**
     * 功能描述: 获取人脸模块状态
     *
     * @param: null
     * @return:
     * @auther: iMliu
     * @date: 2019/9/27 16:18
     */
    @RequestMapping(path = {"/getFaceOption"})
    @ResponseBody
    public void getFaceOption(HttpServletRequest req,HttpServletResponse resp){
        PrintWriter pw =null;
        try {
            pw = resp.getWriter();
            String face = calloutservice.getFaceOption();
            if(face!=null && !"".equals(face)){
                pw.print(face);
                pw.flush();
                pw.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            if(pw!=null){
                pw.close();
            }
        }
    }
}
