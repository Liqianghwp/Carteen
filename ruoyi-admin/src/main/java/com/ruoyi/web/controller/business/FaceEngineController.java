package com.ruoyi.web.controller.business;

import com.arcsoft.face.FaceEngine;
import com.arcsoft.face.FaceFeature;
import com.arcsoft.face.FaceInfo;
import com.arcsoft.face.FaceSimilar;
import com.arcsoft.face.toolkit.ImageInfo;
import com.diandong.domain.po.FaceRecognitionPO;
import com.diandong.service.FaceRecognitionMpService;
import com.ruoyi.common.config.RuoYiConfig;
import com.ruoyi.common.constant.Constants;
import com.ruoyi.common.core.domain.BaseResult;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.common.utils.file.FileUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

import static com.arcsoft.face.toolkit.ImageFactory.getRGBData;

/**
 * @Classname FaceEngineController
 * @Description TODO
 * @Date 2022/6/20 10:08
 * @Created by YuLiu
 */
@Slf4j
@Validated
@RestController
@RequestMapping(value = "/face_engine")
public class FaceEngineController {


    @Resource
    private FaceRecognitionMpService faceRecognitionMpService;
    @Resource
    private FaceEngine faceEngine;


    @GetMapping
    public BaseResult faceAuth(MultipartFile file) {

        FaceRecognitionPO faceRecognitionPO = faceRecognitionMpService.getById(1);

        //人脸检测

        // 本地资源路径
        String localPath = RuoYiConfig.getProfile();
        // 数据库资源地址
        String downloadPath = localPath + StringUtils.substringAfter(faceRecognitionPO.getFacePicture(), Constants.RESOURCE_PREFIX);
        log.info("图片保存地址：{}", downloadPath);
        ImageInfo imageInfo = getRGBData(new File(downloadPath));
        List<FaceInfo> faceInfoList = new ArrayList<FaceInfo>();
        faceEngine.detectFaces(imageInfo.getImageData(), imageInfo.getWidth(), imageInfo.getHeight(), imageInfo.getImageFormat(), faceInfoList);
        //特征提取
        FaceFeature faceFeature = new FaceFeature();
        faceEngine.extractFaceFeature(imageInfo.getImageData(), imageInfo.getWidth(), imageInfo.getHeight(), imageInfo.getImageFormat(), faceInfoList.get(0), faceFeature);
        log.info("原特征值大小：{}", faceFeature.getFeatureData().length);


        //人脸检测
        ImageInfo imageInfo2 = getRGBData(FileUtils.transferToFile(file));
        List<FaceInfo> faceInfoList2 = new ArrayList<FaceInfo>();
        faceEngine.detectFaces(imageInfo2.getImageData(), imageInfo2.getWidth(), imageInfo2.getHeight(), imageInfo2.getImageFormat(), faceInfoList2);
        //特征提取
        FaceFeature faceFeature2 = new FaceFeature();
        faceEngine.extractFaceFeature(imageInfo2.getImageData(), imageInfo2.getWidth(), imageInfo2.getHeight(), imageInfo2.getImageFormat(), faceInfoList2.get(0), faceFeature2);
        log.info("现特征值大小：{}", faceFeature2.getFeatureData().length);


        FaceFeature targetFaceFeature = new FaceFeature();
        targetFaceFeature.setFeatureData(faceFeature.getFeatureData());
        FaceFeature sourceFaceFeature = new FaceFeature();
        sourceFaceFeature.setFeatureData(faceFeature2.getFeatureData());
        FaceSimilar faceSimilar = new FaceSimilar();

        faceEngine.compareFaceFeature(targetFaceFeature, sourceFaceFeature, faceSimilar);

        log.info("相似度：{}", faceSimilar.getScore());
        return BaseResult.success();
    }

}
