package com.mrliu.bean;/**
 * Created by iMliu  on  2019/9/5
 */

/**
 *
 *@description: TODO
 *
 *@author : iMliu
 *@date  : 2019/9/5 17:04
 */
public class JhReponse {
    private String respCode;
    private String respMsg;
    private Jhcallout respData;

    public String getRespCode() {
        return respCode;
    }
    public void setRespCode(String respCode) {
        this.respCode = respCode;
    }
    public String getRespMsg() {
        return respMsg;
    }
    public void setRespMsg(String respMsg) {
        this.respMsg = respMsg;
    }
    public Jhcallout getRespData() {
        return respData;
    }
    public void setRespData(Jhcallout respData) {
        this.respData = respData;
    }
}
