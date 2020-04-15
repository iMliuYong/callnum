package com.mrliu.callnum;

import com.arcsoft.face.*;
import com.arcsoft.face.enums.DetectMode;
import com.arcsoft.face.enums.DetectOrient;
import com.arcsoft.face.enums.ErrorInfo;
import com.arcsoft.face.enums.ImageFormat;
import com.arcsoft.face.toolkit.ImageInfo;
import com.google.gson.Gson;
import com.mrliu.bean.Callnum;
import com.mrliu.service.CalloutService;
import com.mrliu.util.ICSDK;
import com.mrliu.util.NumberMachine;
import com.mrliu.util.PropertiesLoader;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.Base64Utils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;


import static com.arcsoft.face.toolkit.ImageFactory.getGrayData;
import static com.arcsoft.face.toolkit.ImageFactory.getRGBData;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @Auther: iMliu
 * @Date: 2019/8/22 10:41
 * @Description : 人脸识别 --叫号系统
 */

/*
    Controller 类
 */
@Controller
@RequestMapping(value = "/view")
public class Callcontroller {

    @Autowired
    private CalloutService calloutservice;

    //配置 日志
    private static final Logger log = Logger.getLogger(Callcontroller.class);

    //获取配置文件
    private static PropertiesLoader loader = new PropertiesLoader("application.properties");
    /**
     * 功能描述: 叫号系统界面index
     *
     * @param:
     * @return:
     * @auther: iMliu
     * @date: 2019/8/22 10:49
     */
    @RequestMapping(path = {"/index"})
    public String Callnumber() {
        System.out.println("-- 叫号系统 --");
        return "callnum";

    }

    @RequestMapping(path = {"/Face"})
    public String CallnumberFace() {
        System.out.println("-- 呼叫 --");
        return "Face";

    }

    @RequestMapping(path = {"/admin"})
    public String CallnumberSystem(){
        System.out.println("-- 系统管理员 --");
        return "admin";
    }

    @RequestMapping(path = {"/logins"})
    public String CallnumberLogins(){
        System.out.println("-- 登录成功 --");
        return "login_index";
    }

    /**
     * 功能描述: 身份证识别
     *
     * @param:
     * @return:
     * @auther: iMliu
     * @date: 2019/8/28 9:30
     */
    @RequestMapping(path={"/idcard"})
    @ResponseBody
    public void CheckIdcard(HttpServletRequest req, HttpServletResponse resp){
        resp.setCharacterEncoding("UTF-8");
        resp.setContentType("application/json; charset=utf-8");
        log.info("==================取号===================");
        PrintWriter pw =null;
        try {
            pw =resp.getWriter();
            //读取身份证号码
            ICSDK icsdk = ICSDK.getInstance();
            String idphoto = icsdk.GetSfzmzp().trim();
            String xm =icsdk.GetPeopleName().trim();
            String sfzmhm = icsdk.GetSfzmhm().trim();
            String option = calloutservice.getFaceOption();

            if("".equals(idphoto)||"".equals(xm)||"".equals(sfzmhm)){
                pw.print("");
            }else{
                List<Callnum> list = new ArrayList<>();
                Callnum callnum = new Callnum();
                callnum.setQhrxm(xm);
                callnum.setSfzmhm(sfzmhm);
                callnum.setPhotodata(idphoto);
                callnum.setOption(option);
                list.add(callnum);
                Gson gson = new Gson();
                pw.print(gson.toJson(list));
            }
            pw.flush();
        } catch (IOException e) {
            log.info("身份证阅读失败");
            e.printStackTrace();
        }finally {
            if(pw!=null){
                pw.close();
            }
        }

    }


    /**
     * 功能描述: 人脸识别
     *
     * @param:
     * @return:
     * @auther: iMliu
     * @date: 2019/8/28 9:31
     */
    @RequestMapping(path = {"/face"})
    @ResponseBody
    public void FaceCheck(@RequestParam("imageURL") String base64Data, @RequestParam("photoData") String photoData,HttpServletRequest request, HttpServletResponse response)throws Exception {
        response.setHeader("Content-type", "application/json;charset=UTF-8");
        response.setCharacterEncoding("UTF-8");

        log.info("开始人脸比对");
        //人脸appId
        String appId = "EM78PPoYmPTft68b2MuaHJRxMyBCRp5jDckPoL77Dn5e";
        //人脸sdkKey
        String sdkKey = "EEM7mUL67xbwZk43NE6jRmRBP92KFafV1QCtjnYrRKG7";

        PrintWriter pw = null;
        try {
            pw = response.getWriter();
            log.info("对传 图进行判断解析");

        FaceEngine faceEngine = new FaceEngine("d:\\pic");
            int activeCode = faceEngine.activeOnline(appId, sdkKey);
            if (activeCode != ErrorInfo.MOK.getValue() && activeCode != ErrorInfo.MERR_ASF_ALREADY_ACTIVATED.getValue()) {
                System.out.println("引擎激活失败");
            }

        EngineConfiguration engineConfiguration = new EngineConfiguration();
        engineConfiguration.setDetectMode(DetectMode.ASF_DETECT_MODE_IMAGE);
        engineConfiguration.setDetectFaceOrientPriority(DetectOrient.ASF_OP_0_ONLY);

        //功能配置
        FunctionConfiguration functionConfiguration = new FunctionConfiguration();
        functionConfiguration.setSupportAge(true);
        functionConfiguration.setSupportFace3dAngle(true);
        functionConfiguration.setSupportFaceDetect(true);
        functionConfiguration.setSupportFaceRecognition(true);
        functionConfiguration.setSupportGender(true);
        functionConfiguration.setSupportLiveness(true);
        functionConfiguration.setSupportIRLiveness(true);
        engineConfiguration.setFunctionConfiguration(functionConfiguration);
        int initCode = faceEngine.init(engineConfiguration);

        if (initCode != ErrorInfo.MOK.getValue()) {
            System.out.println("初始化引擎失败");
        }

            String data = "";
            byte[] bs_rl;
            byte[] bs_sfz;
            if (base64Data == null || "".equals(base64Data)) {
                throw new Exception("获取人脸图片失败，人脸图片数据为空");
            } else {
                String[] d = base64Data.split("base64,");
                if (d != null && d.length == 2) {
                    data = d[1];
                    bs_rl = Base64Utils.decodeFromString(data);
                } else {
                    throw new Exception("人脸图片获取失败，数据不合法");
                }

                if(photoData == null || "".equals(photoData)){
                    throw  new Exception("获取身份证照片失败，身份证照片为空");
                }else{
                    bs_sfz = Base64Utils.decodeFromString(photoData);
                }


                //人脸检测 获取 前台摄像头捕获的图像
                ImageInfo imageInfo = getRGBData(new ByteArrayInputStream(bs_rl));
                List<FaceInfo> faceInfoList = new ArrayList<FaceInfo>();
                int detectCode = faceEngine.detectFaces(imageInfo.getImageData(), imageInfo.getWidth(), imageInfo.getHeight(), ImageFormat.CP_PAF_BGR24, faceInfoList);
                System.out.println(faceInfoList);

                //特征提取 1
                FaceFeature faceFeature = new FaceFeature();
                if(faceInfoList!=null&&!faceInfoList.isEmpty()){
                    int extractCode = faceEngine.extractFaceFeature(imageInfo.getImageData(), imageInfo.getWidth(), imageInfo.getHeight(), ImageFormat.CP_PAF_BGR24, faceInfoList.get(0), faceFeature);
                }
                System.out.println("特征值大小：" + faceFeature.getFeatureData().length);

                //人脸检测2
                ImageInfo imageInfo2 = getRGBData(new ByteArrayInputStream(bs_sfz));
                List<FaceInfo> faceInfoList2 = new ArrayList<FaceInfo>();
                int detectCode2 = faceEngine.detectFaces(imageInfo2.getImageData(), imageInfo2.getWidth(), imageInfo2.getHeight(), ImageFormat.CP_PAF_BGR24, faceInfoList2);
                System.out.println(faceInfoList2);

                //特征提取2
                FaceFeature faceFeature2 = new FaceFeature();
                if(faceInfoList2!=null &&!faceInfoList2.isEmpty()){
                    int extractCode2 = faceEngine.extractFaceFeature(imageInfo2.getImageData(), imageInfo2.getWidth(), imageInfo2.getHeight(), ImageFormat.CP_PAF_BGR24, faceInfoList2.get(0), faceFeature2);
                }
                System.out.println("特征值大小：" + faceFeature2.getFeatureData().length);


                //特征比对( 人脸比对)
                FaceFeature targetFaceFeature = new FaceFeature();
                targetFaceFeature.setFeatureData(faceFeature.getFeatureData());
                FaceFeature sourceFaceFeature = new FaceFeature();
                sourceFaceFeature.setFeatureData(faceFeature2.getFeatureData());
                FaceSimilar faceSimilar = new FaceSimilar();
                int compareCode = faceEngine.compareFaceFeature(targetFaceFeature, sourceFaceFeature, faceSimilar);
                System.out.println("相似度：" + faceSimilar.getScore()+"-------");

                faceInfoList2.clear();
                faceInfoList.clear();

                float face_ =Float.parseFloat (loader.getProperty("face"));

                //引擎卸载
                int unInitCode = faceEngine.unInit();

                if(faceSimilar.getScore()>face_){
                    pw.print("1");
                }else{
                    pw.print("0");
                }
            }
            pw.flush();
        } catch (IOException | IndexOutOfBoundsException | InterruptedException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (pw != null) {
                pw.close();
            }
        }
    }
}
