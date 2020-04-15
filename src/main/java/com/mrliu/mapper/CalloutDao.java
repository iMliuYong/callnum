package com.mrliu.mapper;

import com.mrliu.bean.Callnum;
import com.mrliu.bean.Jhcallout;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by iMliu  on  2019/8/29
 */
@Mapper
@Repository
public interface CalloutDao {
    String getNowNum();//获取正在排队的编号

    int getWaitNum();//获取排队数

    Integer getSfzmhmNum(@Param("sfzmhm") String sfzmhm);

    int getSkcs();

    String getoldNum();

    boolean insertCall(Callnum callout);//插入排队号

    List<Callnum> getQueueNum();

    boolean JhQueue(@Param("ywckbh") String ywckbh,@Param("qhxxxlh") String qhxxxlh);

    boolean SkipQueue(@Param("ywckbh") String ywckbh,@Param("qhxxxlh") String qhxxxlh);

    List<Callnum> getRecallNum(@Param("callwin") String callwin);

    String getFaceOption();

    void upFaceOption(@Param("mrz") String mrz);
}
