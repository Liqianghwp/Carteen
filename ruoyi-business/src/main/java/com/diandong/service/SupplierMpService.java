package com.diandong.service;

import com.baomidou.mybatisplus.extension.conditions.query.LambdaQueryChainWrapper;
import com.diandong.configuration.CommonService;
import com.diandong.domain.dto.SupplierDTO;
import com.diandong.domain.po.SupplierPO;
import com.diandong.domain.vo.SupplierVO;

import java.util.List;

/**
 * 供应商管理Service接口类
 *
 * @author YuLiu
 * @date 2022-05-19
 */
public interface SupplierMpService extends CommonService<SupplierPO> {


    /**
     * 移除移入黑名单
     *
     * @param id
     * @return
     */
    Boolean changeBlack(Long id);


    /**
     * 获取导出内容
     * @param vo
     * @return
     */
    List<SupplierDTO> getExportList(SupplierVO vo);

    /**
     * 拼接查询条件
     *
     * @param vo 查询条件
     * @return
     */
    LambdaQueryChainWrapper<SupplierPO> onSelectWhere(SupplierVO vo);

}
