package com.diandong.mapper;

import com.diandong.configuration.CommonMapper;
import com.diandong.domain.po.HealthCertMsgPO;
import org.apache.ibatis.annotations.Mapper;

/**
 * 健康证到期消息Mapper
 *
 * @author YuLiu
 * @date 2022-05-11
 */
@Mapper
public interface HealthCertMsgMapper extends CommonMapper<HealthCertMsgPO> {

}