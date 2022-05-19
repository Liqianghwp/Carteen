package com.diandong.task;

import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.diandong.constant.RichTextConstants;
import com.diandong.domain.po.BusinessConfigPO;
import com.diandong.domain.po.HealthCertMsgPO;
import com.diandong.domain.po.HealthCertificatePO;
import com.diandong.enums.BizConfigEnum;
import com.diandong.service.BusinessConfigMpService;
import com.diandong.service.HealthCertMsgMpService;
import com.diandong.service.HealthCertificateMpService;
import com.ruoyi.system.service.ISysConfigService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * @Classname BizQuartzTask
 * @Description 业务定时任务
 * @Date 2022/5/12 14:16
 * @Created by YuLiu
 */
@Slf4j
@Component("bizTask")
public class BizQuartzTask {

    //    @Resource
//    private BusinessConfigMpService businessConfigMpService;
    @Resource
    private ISysConfigService configService;

    @Resource
    private HealthCertificateMpService healthCertificateMpService;
    @Resource
    private HealthCertMsgMpService healthCertMsgMpService;

    public void healthCertOverTimeTask() {

        log.info("健康证预警定时任务START......");
        String value = configService.selectConfigByKey(BizConfigEnum.HEALTH_CERT_INVALID.key());

//        BusinessConfigPO businessConfigPO = businessConfigMpService.searchBusinessConfig(RichTextConstants.HEALTH_CERTIFICATE);
        if (StringUtils.isNotBlank(value)) {
            try {
//                获取预警时间
                Long overTime = Long.valueOf(value);
//                查询健康证信息
                List<HealthCertificatePO> list = healthCertificateMpService.list();
                LocalDateTime now = LocalDateTime.now();
//                筛选预警的健康证信息
                List<HealthCertificatePO> collect = list.stream().filter(healthCertificatePO -> healthCertificatePO.getValidityEndTime().isBefore(now.plusDays(overTime))).collect(Collectors.toList());

                if (CollectionUtils.isNotEmpty(collect)) {
                    List<HealthCertMsgPO> msgList = new ArrayList<>();

                    for (HealthCertificatePO healthCertificatePO : collect) {
                        HealthCertMsgPO msg = new HealthCertMsgPO();

                        msg.setHealthCertId(healthCertificatePO.getId());
                        msg.setName(healthCertificatePO.getName());
                        msg.setPhone(healthCertificatePO.getPhone());
                        msg.setWarningTime(now);
                        msg.setRemindDay(String.valueOf(healthCertificatePO.getValidityEndTime().compareTo(now)));
                        msgList.add(msg);
                    }
                    healthCertMsgMpService.saveBatch(msgList);
                }
            } catch (Exception e) {
                log.warn("健康证预警定时任务异常：{}", e.getMessage(), e);
            }
        }
        log.info("健康证预警定时任务END......");
    }

}
