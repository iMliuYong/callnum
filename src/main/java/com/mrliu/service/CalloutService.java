package com.mrliu.service;/**
 * Created by iMliu  on  2019/8/29
 */

import com.mrliu.bean.Callnum;
import com.mrliu.bean.Jhcallout;
import com.mrliu.mapper.CalloutDao;
import com.mrliu.util.PropertiesLoader;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 *
 *@description: 取号Service
 *
 *@author : iMliu
 *@date  : 2019/8/29 20:26
 */
@Service
public class CalloutService {

    //配置 日志
    private static final Logger log = Logger.getLogger(CalloutService.class);

    @Autowired
    private CalloutDao calloutdao;

    //获取配置文件
    private static PropertiesLoader loader = new PropertiesLoader("application.properties");

    public String getNowNum() {
        return calloutdao.getNowNum();
    }

    public int getWaitNum() {
        int waitNum = calloutdao.getWaitNum();
        return waitNum;
    }

    public Integer getSfzmhmNum(String sfzmhm) {
        return calloutdao.getSfzmhmNum(sfzmhm);
    }

    public boolean getSkcs() {
        //刷卡限制次数
        int  skcs = Integer.parseInt(loader.getProperty("skcs"));
        int cs = calloutdao.getSkcs();
        if(skcs>cs) {
            return true;
        }else {
            log.info("当天刷卡异常 || 超出当前刷卡时间范围");
            return false;
        }
    }

    public String getoldNum() {
        return calloutdao.getoldNum();
    }

    public boolean insertCallout(Callnum callout) {
        return calloutdao.insertCall(callout);
    }

    public Jhcallout getQueueNum(String ywckbh, String type) {
        Jhcallout jhcallout;
        List<Callnum> list = calloutdao.getQueueNum();
        String qhxxxlh = "";	//取号序列号
        String qhrxm="";		//取号人姓名
        String dlrsfzmhm="";	//代理人身份证号码
        String pdh = "";		//排队号
        if(list!=null && !list.isEmpty()){
            jhcallout = new Jhcallout();
            qhxxxlh = list.get(0).getQhxxxlh();
            boolean call;
            if("jh".equals(type)){
                call = calloutdao.JhQueue(ywckbh,qhxxxlh);
            }else{
                call = calloutdao.SkipQueue(ywckbh,qhxxxlh);
            }
            if(list.get(0).getQhrxm()!=null) {
                qhrxm=list.get(0).getQhrxm();
            }
            if(list.get(0).getDlrsfzmhm()!=null) {
                dlrsfzmhm = list.get(0).getDlrsfzmhm();
            }

            jhcallout.setSbkzjsjip("");
            jhcallout.setQhxxxlh(list.get(0).getQhxxxlh());
            jhcallout.setPdh(qhxxxlh.substring(8));
            jhcallout.setYwlb(list.get(0).getYwlb());//01机动车，02驾驶证，04-违法业务
            jhcallout.setSfzmhm(list.get(0).getSfzmhm());
            jhcallout.setDlrsfzmhm(dlrsfzmhm);
            jhcallout.setQhrxm(qhrxm);
            jhcallout.setQhsj(list.get(0).getQhsj());
            jhcallout.setRylb(list.get(0).getRylb());
            log.info("叫号修改state="+call);
            return jhcallout;
        }else{
            return null;
        }
    }

    public List<Callnum> getRecallNum(String callwin) {
        return calloutdao.getRecallNum(callwin);
    }

    public String getFaceOption() {
        return calloutdao.getFaceOption();
    }

    public void upFaceOption(String mrz) {
        calloutdao.upFaceOption(mrz);
    }
}
