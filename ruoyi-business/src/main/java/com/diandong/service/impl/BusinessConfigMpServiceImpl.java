package com.diandong.service.impl;

import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.conditions.query.LambdaQueryChainWrapper;
import com.diandong.configuration.CommonServiceImpl;
import com.diandong.constant.Constants;
import com.diandong.constant.RichTextConstants;
import com.diandong.domain.po.BusinessConfigPO;
import com.diandong.domain.vo.BusinessConfigVO;
import com.diandong.mapper.BusinessConfigMapper;
import com.diandong.mapstruct.BusinessConfigMsMapper;
import com.diandong.service.BusinessConfigMpService;
import com.ruoyi.common.core.domain.BaseResult;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;

/**
 * service实现类
 *
 * @author YuLiu
 * @date 2022-03-24
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class BusinessConfigMpServiceImpl extends CommonServiceImpl<BusinessConfigMapper, BusinessConfigPO>
        implements BusinessConfigMpService {

    @Override
    public BusinessConfigPO searchBusinessConfig(String configName) {
        BusinessConfigPO businessConfigPO = new BusinessConfigPO();
        if (StringUtils.isNotBlank(configName)) {
            LambdaQueryChainWrapper<BusinessConfigPO> wrapper = lambdaQuery().eq(BusinessConfigPO::getConfigName, configName);

            if(RichTextConstants.START_PAGE.equals(configName)){
                wrapper.eq(BusinessConfigPO::getDataState,Constants.DEFAULT_YES);
            }
            List<BusinessConfigPO> list = wrapper.list();

            if (CollectionUtils.isNotEmpty(list)) {
                businessConfigPO = list.get(0);
            }
        }
        return businessConfigPO;
    }

    @Override
    public BaseResult saveAndUpdate(BusinessConfigVO vo) {
        BusinessConfigPO po = BusinessConfigMsMapper.INSTANCE.vo2po(vo);

        BusinessConfigPO oldConfig = lambdaQuery().eq(BusinessConfigPO::getConfigName, po.getConfigName()).one();
        Boolean result = false;
        if (Objects.isNull(oldConfig)) {
            result = save(po);
        } else {
            po.setId(oldConfig.getId());
            result = updateById(po);
        }
        if (result) {
            return BaseResult.success(po);
        } else {
            return BaseResult.error("操作失败");
        }
    }

    @Override
    public BaseResult saveAndUpdateStartPage(BusinessConfigVO vo) {

        BusinessConfigPO po = BusinessConfigMsMapper.INSTANCE.vo2po(vo);
        boolean result = saveOrUpdate(po);
        if (result) {
            return BaseResult.success(po);
        } else {
            return BaseResult.error("操作失败");
        }
    }

    @Override
    public BaseResult onOffStartPageState(String id) {

        BusinessConfigPO businessConfig = getById(id);
        if (businessConfig.getDataState() == Constants.DEFAULT_YES) {
//            开启变关闭不做什么处理
            businessConfig.setDataState(Constants.DEFAULT_NO);
        } else {
//            关闭变开启 将其他全部设置为关闭

            lambdaUpdate().set(BusinessConfigPO::getDataState, Constants.DEL_NO)
                    .eq(BusinessConfigPO::getConfigName, RichTextConstants.START_PAGE)
                    .update();

            businessConfig.setDataState(Constants.DEFAULT_YES);
        }
        boolean result = updateById(businessConfig);

        if (result) {
            return BaseResult.success();
        } else {
            return BaseResult.error();
        }
    }
}
