package com.diandong.configuration;

import com.arcsoft.face.ActiveFileInfo;
import com.arcsoft.face.EngineConfiguration;
import com.arcsoft.face.FaceEngine;
import com.arcsoft.face.FunctionConfiguration;
import com.arcsoft.face.enums.DetectMode;
import com.arcsoft.face.enums.DetectOrient;
import com.arcsoft.face.enums.ErrorInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.File;

/**
 * @Classname FaceEngineConfig
 * @Description 人脸认证配置
 * @Date 2022/6/17 19:05
 * @Created by YuLiu
 */
@Slf4j
@Configuration
public class FaceEngineConfig {

    private static final String APPID = "E5rK9rnDHu2fWPXDruvN1NVYde2YZuvFGFZvs1qDwkgd";
    private static final String SDK_KEY = "5KCzin5bCm3dayfNy4rBT8V8Dm7rccE5cK66r1enmnoX";

    @Bean
    public FaceEngine faceEngine() {
        log.info("人脸认证引擎加载开始");
        FaceEngine faceEngine = new FaceEngine(System.getProperty("user.dir") + File.separator + "ruoyi-admin/src/main/resources/faceEngine/WIN64");
        int errorCode = faceEngine.activeOnline(APPID, SDK_KEY);
        if (errorCode != ErrorInfo.MOK.getValue() && errorCode != ErrorInfo.MERR_ASF_ALREADY_ACTIVATED.getValue()) {
            log.error("引擎激活失败");
        }

        ActiveFileInfo activeFileInfo = new ActiveFileInfo();
        errorCode = faceEngine.getActiveFileInfo(activeFileInfo);
        if (errorCode != ErrorInfo.MOK.getValue() && errorCode != ErrorInfo.MERR_ASF_ALREADY_ACTIVATED.getValue()) {
            log.error("获取激活文件信息失败");
        }

        //引擎配置
        EngineConfiguration engineConfiguration = new EngineConfiguration();
        engineConfiguration.setDetectMode(DetectMode.ASF_DETECT_MODE_IMAGE);
        //engineConfiguration.setDetectMode(DetectMode.ASF_DETECT_MODE_VIDEO);
        engineConfiguration.setDetectFaceOrientPriority(DetectOrient.ASF_OP_0_ONLY);
        engineConfiguration.setDetectFaceMaxNum(10);
        engineConfiguration.setDetectFaceScaleVal(16);
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


        //初始化引擎
        errorCode = faceEngine.init(engineConfiguration);

        if (errorCode != ErrorInfo.MOK.getValue()) {
            log.error("初始化引擎失败");
        }
        log.info("人脸认证引擎加载完成");
        return faceEngine;
    }

//    public


}
