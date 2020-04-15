package com.mrliu.callnum;

import com.arcsoft.face.EngineConfiguration;
import com.arcsoft.face.FaceEngine;
import com.arcsoft.face.FunctionConfiguration;
import com.arcsoft.face.enums.DetectMode;
import com.arcsoft.face.enums.DetectOrient;
import com.arcsoft.face.enums.ErrorInfo;
import com.mrliu.CallnumApplication;
import com.mrliu.util.NumberMachine;
import com.mrliu.util.PrintUtil;
import org.apache.log4j.Logger;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

public class ServletInitializer extends SpringBootServletInitializer {

	private  static final  Logger log = Logger.getLogger(ServletInitializer.class);

	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
		logger.info("======================开始打开打印机端口=====================");
		int hand_id;
		try {
			hand_id = 1000;
			//hand_id = PrintUtil.print_open();
			logger.info("打印机句柄为:"+hand_id);
				//将打印机返回句柄存储为单例
				//NumberMachine.getInstance().getCommonManager().generatePrinter(hand_id);
			logger.info("======================打开打印机端口成功=====================");

			} catch (Exception e) {
				logger.info(e.getMessage());
			}
		return application.sources(CallnumApplication.class);
	}

}
