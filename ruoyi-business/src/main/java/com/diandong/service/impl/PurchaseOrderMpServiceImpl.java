package com.diandong.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import com.diandong.configuration.CommonServiceImpl;
import com.diandong.constant.Constants;
import com.diandong.constant.PurchaseOrderStateConstant;
import com.diandong.domain.dto.PurchaseOrderDTO;
import com.diandong.domain.po.CanteenPO;
import com.diandong.domain.po.PurchaseOrderPO;
import com.diandong.domain.po.PurchasingPO;
import com.diandong.domain.po.SupplierPO;
import com.diandong.domain.vo.PurchaseOrderVO;
import com.diandong.mapper.PurchaseOrderMapper;
import com.diandong.mapstruct.PurchaseOrderMsMapper;
import com.diandong.mapstruct.PurchasingMsMapper;
import com.diandong.service.CanteenMpService;
import com.diandong.service.PurchaseOrderMpService;
import com.diandong.service.PurchasingMpService;
import com.diandong.service.SupplierMpService;
import com.ruoyi.common.core.domain.BaseResult;
import com.ruoyi.common.utils.BizIdUtil;
import com.ruoyi.common.utils.SecurityUtils;
import com.ruoyi.common.utils.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * 采购订单管理service实现类
 *
 * @author YuLiu
 * @date 2022-06-22
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class PurchaseOrderMpServiceImpl extends CommonServiceImpl<PurchaseOrderMapper, PurchaseOrderPO>
        implements PurchaseOrderMpService {

    @Resource
    private BizIdUtil bizIdUtil;
    @Resource
    private PurchasingMpService purchasingMpService;
    @Resource
    private SupplierMpService supplierMpService;
    @Resource
    private CanteenMpService canteenMpService;

    @Override
    public BaseResult createPurchaseOrder(PurchaseOrderVO vo) {

        PurchaseOrderPO purchaseOrderPO = PurchaseOrderMsMapper.INSTANCE.vo2po(vo);

        CanteenPO canteen = canteenMpService.getById(SecurityUtils.getCanteenId());
        purchaseOrderPO.setGroupId(canteen.getPId());
        purchaseOrderPO.setOrderId(bizIdUtil.getPurchaseNo());
        purchaseOrderPO.setState(PurchaseOrderStateConstant.PURCHASING);

//        查询原材料是否被删除 被采购
        List<String> purchaseIds = Arrays.asList(vo.getDetails().split(","));
        List<PurchasingPO> list = purchasingMpService.lambdaQuery().in(PurchasingPO::getId, purchaseIds).list();
        List<PurchasingPO> collect = list.stream().filter(purchasingPO -> Constants.DEL_YES == purchasingPO.getDelFlag() || Constants.DEFAULT_YES == purchasingPO.getIsPurchase()).collect(Collectors.toList());
        if (CollectionUtil.isNotEmpty(collect)) {
            return BaseResult.error("原材料:" + collect.stream().map(PurchasingPO::getRawMaterialName).collect(Collectors.joining(",")) + "，已被采购");
        }
        purchasingMpService.lambdaUpdate().set(PurchasingPO::getIsPurchase, Constants.DEFAULT_YES).in(PurchasingPO::getId, purchaseIds).update();

        BigDecimal total = BigDecimal.ZERO;
        for (PurchasingPO purchasingPO : list) {
            total = total.add(purchasingPO.getSubtotal());
        }
        purchaseOrderPO.setOrderPrice(total);

        if (StringUtils.isBlank(vo.getSupplierName())) {
            SupplierPO supplier = supplierMpService.getById(vo.getSupplierId());
            purchaseOrderPO.setSupplierName(Objects.nonNull(supplier) ? supplier.getSupplierName() : null);
        }

        save(purchaseOrderPO);
        return BaseResult.success();
    }

    @Override
    public BaseResult selectPurchaseOrder(Long id) {

        PurchaseOrderPO purchaseOrderPO = getById(id);
        PurchaseOrderDTO purchaseOrderDTO = PurchaseOrderMsMapper.INSTANCE.po2dto(purchaseOrderPO);
        SupplierPO supplier = supplierMpService.getById(purchaseOrderDTO.getSupplierId());
        purchaseOrderDTO.setSupplierContact(supplier.getContactName());
        purchaseOrderDTO.setSupplierPhone(supplier.getContactPhone());

        String details = purchaseOrderPO.getDetails();
        if (StringUtils.isNotBlank(details)) {
            List<String> purchaseIds = Arrays.asList(details.split(","));
            List<PurchasingPO> list = purchasingMpService.lambdaQuery()
                    .in(PurchasingPO::getId, purchaseIds)
                    .eq(PurchasingPO::getDelFlag, Constants.DEL_NO)
                    .list();
            if (CollectionUtil.isNotEmpty(list)) {
                purchaseOrderDTO.setPurchasingList(PurchasingMsMapper.INSTANCE.poList2dtoList(list));
            }
        }

        return BaseResult.success(purchaseOrderDTO);
    }
}
