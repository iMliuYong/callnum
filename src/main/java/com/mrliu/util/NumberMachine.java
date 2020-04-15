package com.mrliu.util;/**
 * Created by iMliu  on  2019/8/30
 */

/**
 *
 *@description: 定义三个成员变量分别指向三个NumberManager对象(个人业务，综合业务，VIP业务)
 *
 *@author : iMliu
 *@date  : 2019/8/30 9:16
 */
public class NumberMachine {
    private NumberMachine() {}

    private static NumberMachine instance = new NumberMachine();
    public static NumberMachine getInstance() {
        return instance;
    }

    private NumberManager commonManager = new NumberManager();
    private NumberManager expressManager = new NumberManager();
    private NumberManager vipManager = new NumberManager();
    public NumberManager getCommonManager() {
        return commonManager;
    }


    public NumberManager getExpressManager() {
        return expressManager;
    }
    public NumberManager getVipManager() {
        return vipManager;
    }
}
