package com.mrliu.util;/**
 * Created by iMliu  on  2019/9/5
 */
import java.io.File;
import java.io.IOException;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.SourceDataLine;
import javax.sound.sampled.UnsupportedAudioFileException;
/**
 *
 *@description: TODO
 *
 *@author : iMliu
 *@date  : 2019/9/5 17:24
 */
public class SpeakUtil {

    //获取配置文件
    private static PropertiesLoader loader = new PropertiesLoader("application.properties");

    /**
     * @Title: Call_Waiter
     * @date 2018年11月26日
     * @Description: 调用Windows自带的语音进行叫号
     * @param @param winNum 窗口号
     * @param @param num    排队号
     * @return void
     * @throws
     */
    public static synchronized void Call_Waiter(String winNum,String num) {
        /*
         * o  e  b  t  u  i  s  v  g  n  w
         * 10 11 12 13 14 15 16 17 18 19 20
         */

        if(winNum.length()==2) {
            if("10".equals(winNum)) {
                winNum = "o";
            }else if("11".equals(winNum)) {
                winNum = "e";
            }else if("12".equals(winNum)) {
                winNum = "b";
            }else if("13".equals(winNum)) {
                winNum = "t";
            }else if("14".equals(winNum)) {
                winNum = "u";
            }else if("15".equals(winNum)) {
                winNum = "i";
            }else if("16".equals(winNum)) {
                winNum = "s";
            }else if("17".equals(winNum)) {
                winNum = "v";
            }else if("18".equals(winNum)) {
                winNum = "g";
            }else if("19".equals(winNum)) {
                winNum = "n";
            }else if("20".equals(winNum)) {
                winNum = "w";
            }
        }

        String BF = "F请"+num+"号到"+winNum+"L";
        String sysPath = loader.getProperty("sysPath");//获取wav 语音文件路径

        for(int i= 0;i<3;i++) {
            for (char letter : BF.toCharArray()) {
                String filePath = sysPath+"AudioSource\\"+letter+".wav";
                if (!filePath.equals("")) {
                    //Get audio input stream
                    AudioInputStream audioInputStream;
                    try {
                        audioInputStream = AudioSystem.getAudioInputStream(new File(filePath));
                        //Get audio coding object
                        AudioFormat audioFormat = audioInputStream.getFormat();
                        //Set data entry
                        DataLine.Info dataLineInfo = new DataLine.Info(SourceDataLine.class, audioFormat,
                                AudioSystem.NOT_SPECIFIED);
                        SourceDataLine sourceDataLine = (SourceDataLine) AudioSystem.getLine(dataLineInfo);
                        sourceDataLine.open(audioFormat);
                        sourceDataLine.start();
                        //Read from the data sent to the mixer input stream
                        int count;
                        byte tempBuffer[] = new byte[1024];
                        while ((count = audioInputStream.read(tempBuffer, 0, tempBuffer.length)) != -1) {
                            if (count > 0) {
                                sourceDataLine.write(tempBuffer, 0, count);
                            }
                        }
                        //Empty the data buffer, and close the input
                        sourceDataLine.drain();
                        sourceDataLine.close();
                    } catch (UnsupportedAudioFileException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    } catch (LineUnavailableException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }
}
