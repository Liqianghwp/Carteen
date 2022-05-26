package com.diandong.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.diandong.configuration.CommonService;
import com.diandong.domain.dto.ReserveSampleDTO;
import com.diandong.domain.po.ReserveSamplePO;

import java.util.List;

/**
 * 预留样品Service接口类
 *
 * @author YuLiu
 * @date 2022-05-11
 */
public interface ReserveSampleMpService extends CommonService<ReserveSamplePO> {


    /**
     * 重新更新分页信息
     *
     * @param page 已查询的分页信息
     * @return
     */
    Page<ReserveSampleDTO> resetPage(Page<ReserveSamplePO> page);


    List<ReserveSampleDTO> changeDTO(List<ReserveSamplePO> list);

}
