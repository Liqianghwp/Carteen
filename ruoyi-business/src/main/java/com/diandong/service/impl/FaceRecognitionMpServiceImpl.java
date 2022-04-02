package com.diandong.service.impl;

import com.diandong.configuration.CommonServiceImpl;
import com.diandong.domain.po.FaceRecognitionPO;
import com.diandong.mapper.FaceRecognitionMapper;
import com.diandong.service.FaceRecognitionMpService;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

/**
 * service实现类
 *
 * @author YuLiu
 * @date 2022-04-01
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class FaceRecognitionMpServiceImpl extends CommonServiceImpl<FaceRecognitionMapper, FaceRecognitionPO>
        implements FaceRecognitionMpService {

}
