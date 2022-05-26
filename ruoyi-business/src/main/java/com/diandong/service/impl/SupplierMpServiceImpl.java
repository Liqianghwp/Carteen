package com.diandong.service.impl;

import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.baomidou.mybatisplus.core.toolkit.ObjectUtils;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.conditions.query.LambdaQueryChainWrapper;
import com.diandong.configuration.CommonServiceImpl;
import com.diandong.constant.Constants;
import com.diandong.domain.dto.SupplierDTO;
import com.diandong.domain.po.SupplierPO;
import com.diandong.domain.vo.SupplierVO;
import com.diandong.mapper.SupplierMapper;
import com.diandong.mapstruct.SupplierMsMapper;
import com.diandong.service.SupplierMpService;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * 供应商管理service实现类
 *
 * @author YuLiu
 * @date 2022-05-19
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class SupplierMpServiceImpl extends CommonServiceImpl<SupplierMapper, SupplierPO>
        implements SupplierMpService {


    @Override
    public Boolean changeBlack(Long id) {
        SupplierPO supplier = getById(id);
        String isBlack = supplier.getIsBlack();
        switch (isBlack) {
            case Constants.WHITELIST:
                supplier.setIsBlack(Constants.BLACKLIST);
                supplier.setMoveTime(LocalDateTime.now());
                break;
            case Constants.BLACKLIST:
                supplier.setIsBlack(Constants.WHITELIST);
                break;
        }
        return updateById(supplier);
    }

    @Override
    public List<SupplierDTO> getExportList(SupplierVO vo) {
        List<Long> ids = vo.getIds();
        List<SupplierPO> list;
        if (CollectionUtils.isNotEmpty(ids)) {
            list = lambdaQuery().in(SupplierPO::getId, ids).list();
        } else {
            list = onSelectWhere(vo).list();
        }
        List<SupplierDTO> supplierDTOArrayList = new ArrayList<>();

        list.forEach(supplierPO -> {
            supplierDTOArrayList.add(SupplierMsMapper.INSTANCE.po2dto(supplierPO));
        });
        return supplierDTOArrayList;
    }

    public LambdaQueryChainWrapper<SupplierPO> onSelectWhere(SupplierVO vo) {
        LambdaQueryChainWrapper<SupplierPO> queryWrapper = lambdaQuery().orderByDesc(SupplierPO::getId);
        if (Objects.isNull(vo)) {
            return queryWrapper;
        }
        queryWrapper.eq(ObjectUtils.isNotEmpty(vo.getId()), SupplierPO::getId, vo.getId());
        queryWrapper.like(StringUtils.isNotBlank(vo.getSupplierName()), SupplierPO::getSupplierName, vo.getSupplierName());
        queryWrapper.eq(StringUtils.isNotBlank(vo.getAccount()), SupplierPO::getAccount, vo.getAccount());
        queryWrapper.like(StringUtils.isNotBlank(vo.getContactName()), SupplierPO::getContactName, vo.getContactName());
        queryWrapper.eq(StringUtils.isNotBlank(vo.getContactPhone()), SupplierPO::getContactPhone, vo.getContactPhone());
        queryWrapper.eq(StringUtils.isNotBlank(vo.getIsBlack()), SupplierPO::getIsBlack, vo.getIsBlack());
        queryWrapper.eq(ObjectUtils.isNotEmpty(vo.getMoveTime()), SupplierPO::getMoveTime, vo.getMoveTime());
        queryWrapper.eq(StringUtils.isNotBlank(vo.getRemark()), SupplierPO::getRemark, vo.getRemark());
        return queryWrapper;
    }

}
