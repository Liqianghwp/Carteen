package com.diandong.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.diandong.constant.Constants;
import com.diandong.domain.dto.*;
import com.diandong.domain.po.*;
import com.diandong.domain.vo.*;
import com.diandong.enums.RechargeMethodEnum;
import com.diandong.enums.RechargeTypeEnum;
import com.diandong.mapstruct.HealthIndicatorsMsMapper;
import com.diandong.mapstruct.NutritionAdviceMsMapper;
import com.diandong.mapstruct.PhysicalCardMsMapper;
import com.diandong.service.*;
import com.ruoyi.common.core.domain.BaseResult;
import com.ruoyi.common.core.domain.entity.SysDictData;
import com.ruoyi.common.core.domain.entity.SysUser;
import com.ruoyi.common.utils.BizIdUtil;
import com.ruoyi.system.service.ISysDictDataService;
import com.ruoyi.system.service.ISysUserService;
import lombok.Value;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Service;
import sun.management.VMOptionCompositeData;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * @Classname UserManagementServiceImpl
 * @Description 用户管理实现类
 * @Date 2022/5/31 15:13
 * @Created by YuLiu
 */
@Slf4j
@Service
public class UserManagementServiceImpl implements IUserManagementService {

    @Resource
    private BizIdUtil bizIdUtil;
    @Resource
    private ISysUserService userService;
    @Resource
    private PhysicalCardMpService physicalCardMpService;
    @Resource
    private HealthIndicatorsMpService healthIndicatorsMpService;
    @Resource
    private NutritionAdviceMpService nutritionAdviceMpService;
    @Resource
    private FaceRecognitionMpService faceRecognitionMpService;
    @Resource
    private RechargeAmountRecordsMpService rechargeAmountRecordsMpService;
    @Resource
    private RechargeTimesRecordsMpService rechargeTimesRecordsMpService;
    @Resource
    private UserAmountMpService userAmountMpService;
    @Resource
    private ISysDictDataService dictDataService;
    @Resource
    private NutritionParamsMpService nutritionParamsMpService;

    @Override
    public Boolean saveUser(SysUserVO vo) {

        SysUser sysUser = new SysUser();

        BeanUtils.copyProperties(vo, sysUser);
        sysUser.setPhonenumber(vo.getPhoneNumber());

        userService.insertUser(sysUser);
//        boolean result = userService.save(sysUser);

//        保存实体卡信息
        PhysicalCardVO physicalCard = vo.getPhysicalCard();
        if (Objects.nonNull(physicalCard)) {
            physicalCard.setUserId(sysUser.getUserId());
            PhysicalCardPO physicalCardPO = PhysicalCardMsMapper.INSTANCE.vo2po(physicalCard);
            physicalCardMpService.save(physicalCardPO);
        }

//        保存人脸认证照片
        String faceImage = vo.getFaceImage();
        if (StringUtils.isNotBlank(faceImage)) {
            FaceRecognitionPO faceRecognitionPO = new FaceRecognitionPO();
            faceRecognitionPO.setUserId(sysUser.getUserId());
            faceRecognitionPO.setFacePicture(faceImage);
            faceRecognitionMpService.save(faceRecognitionPO);
        }

//        保存健康信息
        List<HealthIndicatorsVO> healthIndicatorsList = vo.getHealthIndicatorsList();
        if (CollectionUtils.isNotEmpty(healthIndicatorsList)) {
            saveHealthIndicators(healthIndicatorsList, sysUser);
        }

//        保存营养信息
        List<UserNutritionAdviceVO> userNutritionAdviceList = vo.getUserNutritionAdviceList();
        if (CollectionUtils.isNotEmpty(userNutritionAdviceList)) {
            saveUserNutritionAdvice(userNutritionAdviceList, sysUser);
        }

        return true;
    }

    @Override
    public Boolean updateUser(SysUserVO vo) {

        SysUser sysUser = new SysUser();

        BeanUtils.copyProperties(vo, sysUser);
        sysUser.setPhonenumber(vo.getPhoneNumber());
        boolean result = userService.saveOrUpdate(sysUser);

//        保存或者更新人脸图片
        String faceImage = vo.getFaceImage();
        if (StringUtils.isNotBlank(faceImage)) {
            FaceRecognitionPO faceRecognitionPO = faceRecognitionMpService.lambdaQuery()
                    .eq(FaceRecognitionPO::getUserId, sysUser.getUserId())
                    .eq(FaceRecognitionPO::getDelFlag, Constants.DEL_NO)
                    .one();
            if (Objects.nonNull(faceRecognitionPO)) {
                faceRecognitionPO.setFacePicture(faceImage);
            } else {
                faceRecognitionPO = new FaceRecognitionPO();
                faceRecognitionPO.setUserId(sysUser.getUserId());
                faceRecognitionPO.setFacePicture(faceImage);
            }
            faceRecognitionMpService.saveOrUpdate(faceRecognitionPO);
        }

//        更新用户实体卡信息
        PhysicalCardVO physicalCard = vo.getPhysicalCard();
        if (Objects.nonNull(physicalCard)) {
            PhysicalCardPO physicalCardPO = PhysicalCardMsMapper.INSTANCE.vo2po(physicalCard);
            physicalCardMpService.updateById(physicalCardPO);
        }
//        用户健康指标信息
        List<HealthIndicatorsVO> healthIndicatorsList = vo.getHealthIndicatorsList();
        if (CollectionUtils.isNotEmpty(healthIndicatorsList)) {
//            先删除
            LambdaQueryWrapper<HealthIndicatorsPO> queryWrapper = new LambdaQueryWrapper<>();
            queryWrapper.eq(HealthIndicatorsPO::getUserId, sysUser.getUserId());
            healthIndicatorsMpService.remove(queryWrapper);

//            再添加
            saveHealthIndicators(healthIndicatorsList, sysUser);
        }

//        用户营养建议信息
        List<UserNutritionAdviceVO> userNutritionAdviceList = vo.getUserNutritionAdviceList();
        if (CollectionUtils.isNotEmpty(userNutritionAdviceList)) {
//            先删除
            LambdaQueryWrapper<NutritionAdvicePO> queryWrapper = new LambdaQueryWrapper<>();
            queryWrapper.eq(NutritionAdvicePO::getUserId, sysUser.getUserId());
            nutritionAdviceMpService.remove(queryWrapper);
//            再添加
            saveUserNutritionAdvice(userNutritionAdviceList, sysUser);

        }

        return result;
    }


    @Override
    public BaseResult pageList(SysUserVO vo) {

        SysUser user = new SysUser();
        BeanUtils.copyProperties(vo, user);


        List<SysUserDTO> records = new ArrayList<>();

        List<SysUser> userList = userService.userManagementPageList(user, vo.getPageSize(), vo.getPageNum());
        if (CollectionUtils.isNotEmpty(userList)) {

            for (SysUser sysUser : userList) {
                SysUserDTO userDTO = new SysUserDTO();
                BeanUtils.copyProperties(sysUser, userDTO);

                UserAmountPO userAmount = userAmountMpService.lambdaQuery().eq(UserAmountPO::getUserId, sysUser.getUserId()).eq(UserAmountPO::getDelFlag, Constants.DEL_NO).one();

                userDTO.setAmount(Objects.nonNull(userAmount) ? userAmount.getAmount() : BigDecimal.ZERO);

                records.add(userDTO);
            }
        }

        Integer count = userService.userManagementPageCount(user);
        Page<SysUserDTO> page = new Page(vo.getPageNum(), vo.getPageSize(), count);
        page.setRecords(records);
        return BaseResult.success(page);
    }

    @Override
    public BaseResult getById(String userId) {
        SysUserDTO userDTO = new SysUserDTO();
        SysUser user = userService.lambdaQuery().eq(SysUser::getUserId, userId).one();

        BeanUtils.copyProperties(user, userDTO);

//        查询用户实体卡
        PhysicalCardPO physicalCard = physicalCardMpService.lambdaQuery().eq(PhysicalCardPO::getUserId, userId).one();
        if (Objects.nonNull(physicalCard)) {
            userDTO.setPhysicalCard(PhysicalCardMsMapper.INSTANCE.po2dto(physicalCard));
        }


        FaceRecognitionPO faceRecognitionPO = faceRecognitionMpService.lambdaQuery()
                .eq(FaceRecognitionPO::getUserId, user.getUserId())
                .eq(FaceRecognitionPO::getDelFlag, Constants.DEL_NO)
                .one();
        if (Objects.nonNull(faceRecognitionPO)) {
            userDTO.setFaceImage(faceRecognitionPO.getFacePicture());
        }

//        查询健康指标
        List<HealthIndicatorsPO> healthIndicatorsPOList = healthIndicatorsMpService.lambdaQuery()
                .eq(HealthIndicatorsPO::getUserId, userId)
                .eq(HealthIndicatorsPO::getDelFlag, Constants.DEL_NO)
                .list();
        if (CollectionUtils.isNotEmpty(healthIndicatorsPOList)) {
            List<HealthIndicatorsDTO> dtoList = new ArrayList<>();

            healthIndicatorsPOList.forEach(healthIndicatorsPO -> {
                dtoList.add(HealthIndicatorsMsMapper.INSTANCE.po2dto(healthIndicatorsPO));
            });
            userDTO.setHealthIndicatorsList(dtoList);
        }

//        查询营养建议
        List<NutritionAdvicePO> nutritionAdvicePOList = nutritionAdviceMpService.lambdaQuery()
                .eq(NutritionAdvicePO::getUserId, userId)
                .eq(NutritionAdvicePO::getDelFlag, Constants.DEL_NO)
                .list();

        if (CollectionUtils.isNotEmpty(nutritionAdvicePOList)) {
            Map<Long, List<NutritionAdvicePO>> collect = nutritionAdvicePOList.stream().collect(Collectors.groupingBy(NutritionAdvicePO::getMealTimesId));

            List<UserNutritionAdviceDTO> userNutritionAdviceDTOList = new ArrayList<>();
            for (Map.Entry<Long, List<NutritionAdvicePO>> entry : collect.entrySet()) {
                UserNutritionAdviceDTO userNutritionAdviceDTO = new UserNutritionAdviceDTO();

                List<NutritionAdvicePO> value = entry.getValue();

                NutritionAdvicePO nutritionAdvicePO = value.get(0);

                List<NutritionAdviceDTO> nutritionAdviceDTOList = new ArrayList<>();
                value.forEach(nutritionAdvicePO1 -> {
                    nutritionAdviceDTOList.add(NutritionAdviceMsMapper.INSTANCE.po2dto(nutritionAdvicePO1));
                });

                userNutritionAdviceDTO.setMealTimesId(nutritionAdvicePO.getMealTimesId());
                userNutritionAdviceDTO.setMealTimesName(nutritionAdvicePO.getMealTimesName());
                userNutritionAdviceDTO.setNutritionAdviceList(nutritionAdviceDTOList);
                userNutritionAdviceDTOList.add(userNutritionAdviceDTO);
            }

            userDTO.setUserNutritionAdviceList(userNutritionAdviceDTOList);
        }

        return BaseResult.success(userDTO);
    }


    /**
     * 保存健康指标信息
     *
     * @param healthIndicatorsList 健康指标信息
     * @param sysUser              用户信息
     */
    private void saveHealthIndicators(List<HealthIndicatorsVO> healthIndicatorsList, SysUser sysUser) {
        List<HealthIndicatorsPO> healthIndicatorsPOList = new ArrayList<>();
        healthIndicatorsList.forEach(healthIndicatorsVO -> {
            HealthIndicatorsPO healthIndicatorsPO = HealthIndicatorsMsMapper.INSTANCE.vo2po(healthIndicatorsVO);
            healthIndicatorsPO.setUserId(sysUser.getUserId());
            healthIndicatorsPO.setUserName(sysUser.getNickName());

            SysDictData sysDictData = dictDataService.selectDictDataById(healthIndicatorsVO.getIndicatorsId());
            healthIndicatorsPO.setIndicatorsName(sysDictData.getDictLabel());
//            healthIndicatorsPO.setIndicatorUnit()
            healthIndicatorsPOList.add(healthIndicatorsPO);
        });

        healthIndicatorsMpService.saveBatch(healthIndicatorsPOList);
    }

    /**
     * 保存用户营养信息
     *
     * @param userNutritionAdviceList
     * @param sysUser
     */
    private void saveUserNutritionAdvice(List<UserNutritionAdviceVO> userNutritionAdviceList, SysUser sysUser) {

        for (UserNutritionAdviceVO userNutritionAdviceVO : userNutritionAdviceList) {

            List<NutritionAdviceVO> nutritionAdviceList = userNutritionAdviceVO.getNutritionAdviceList();
            if (CollectionUtils.isNotEmpty(nutritionAdviceList)) {
                List<NutritionAdvicePO> nutritionAdvicePOList = new ArrayList<>();

                SysDictData dictData = dictDataService.selectDictDataById(userNutritionAdviceVO.getMealTimesId());

//                设置营养信息
                nutritionAdviceList.forEach(nutritionAdviceVO -> {
                            NutritionAdvicePO nutritionAdvicePO = NutritionAdviceMsMapper.INSTANCE.vo2po(nutritionAdviceVO);
                            nutritionAdvicePO.setUserId(sysUser.getUserId());
                            nutritionAdvicePO.setMealTimesId(dictData.getDictCode());
                            nutritionAdvicePO.setMealTimesName(dictData.getDictLabel());

                            NutritionParamsPO nutritionParamsPO = nutritionParamsMpService.getById(nutritionAdvicePO.getNutritionalId());
                            nutritionAdvicePO.setNutritionalName(nutritionParamsPO.getNutritionName());
                            nutritionAdvicePO.setUnit(nutritionParamsPO.getUnit());

                            nutritionAdvicePOList.add(nutritionAdvicePO);
                        }
                );
                nutritionAdviceMpService.saveBatch(nutritionAdvicePOList);
            }
        }
    }


    @Override
    public BaseResult bindCard(PhysicalCardVO vo) {

        List<PhysicalCardPO> cardList = physicalCardMpService.lambdaQuery()
                .eq(PhysicalCardPO::getCardNo, vo.getCardNo())
                .eq(PhysicalCardPO::getDelFlag, Constants.DEL_NO)
                .list();
        if (CollectionUtils.isNotEmpty(cardList)) {
            return BaseResult.error("该卡号已经发放，请更换其他卡片进行绑定");
        }

        PhysicalCardPO physicalCard = physicalCardMpService.lambdaQuery()
                .eq(PhysicalCardPO::getUserId, vo.getUserId())
                .one();
        if (Objects.nonNull(physicalCard)) {
            physicalCard.setCardNo(vo.getCardNo());
            physicalCard.setDelFlag(Constants.DEL_YES);
        } else {
            physicalCard = PhysicalCardMsMapper.INSTANCE.vo2po(vo);
        }
        physicalCardMpService.saveOrUpdate(physicalCard);


        PhysicalCardDTO physicalCardDTO = PhysicalCardMsMapper.INSTANCE.po2dto(physicalCardMpService.getById(physicalCard.getId()));
        return BaseResult.success(physicalCardDTO);
    }

    @Override
    public BaseResult recharge(BackstageRechargeVO vo) {

        PhysicalCardPO physicalCard = physicalCardMpService.lambdaQuery()
                .eq(PhysicalCardPO::getUserId, vo.getUserId())
                .eq(PhysicalCardPO::getDelFlag, Constants.DEL_NO)
                .one();

        if (Objects.isNull(physicalCard)) {
            return BaseResult.error("请绑定卡号");
        }
        if (Constants.DEFAULT_NO == physicalCard.getState()) {
            return BaseResult.error("实体卡已停用，请联系管理员");
        }
        if (Constants.DEFAULT_YES == physicalCard.getReportLoss()) {
            return BaseResult.error("实体卡已挂失，请联系管理员");
        }
//        充值金额记录
        if (Objects.nonNull(vo.getAmount())) {
            RechargeAmountRecordsPO rechargeAmountRecordsPO = new RechargeAmountRecordsPO();
            rechargeAmountRecordsPO.setSerialNumber(bizIdUtil.getRechargeAmountNo());
            rechargeAmountRecordsPO.setUserId(vo.getUserId());
            rechargeAmountRecordsPO.setRechargeType(RechargeTypeEnum.ELECTRONIC_CARD.type());
            rechargeAmountRecordsPO.setCardNo(physicalCard.getCardNo());
            rechargeAmountRecordsPO.setAmount(vo.getAmount());
            rechargeAmountRecordsPO.setRechargeMethod(RechargeMethodEnum.SYSTEM.type());
            rechargeAmountRecordsPO.setState(Constants.DEFAULT_NO);

            rechargeAmountRecordsMpService.save(rechargeAmountRecordsPO);
        }
//        充值次数记录
        if (Objects.nonNull(vo.getTimes())) {
            RechargeTimesRecordsPO rechargeTimesRecordsPO = new RechargeTimesRecordsPO();
            rechargeTimesRecordsPO.setSerialNumber(bizIdUtil.getRechargeTimesNo());
            rechargeTimesRecordsPO.setUserId(vo.getUserId());
            rechargeTimesRecordsPO.setRechargeMethod(RechargeMethodEnum.SYSTEM.type());
            rechargeTimesRecordsPO.setTime(LocalDateTime.now());
//            rechargeTimesRecordsPO.setAmount(vo.getAmount());
            rechargeTimesRecordsPO.setCount(vo.getTimes());

            rechargeTimesRecordsMpService.save(rechargeTimesRecordsPO);
        }

//        保存用户资产信息
        if (Objects.nonNull(vo.getAmount()) || Objects.nonNull(vo.getTimes())) {
//            当金额 和 次数 不为空时
            UserAmountPO userAmount = userAmountMpService.lambdaQuery()
                    .eq(UserAmountPO::getUserId, vo.getUserId())
                    .one();

            if (Objects.isNull(userAmount)) {
                userAmount = new UserAmountPO();
                userAmount.setUserId(vo.getUserId());
                userAmount.setAmount(Objects.nonNull(vo.getAmount()) ? vo.getAmount() : BigDecimal.ZERO);
                userAmount.setTimes(Objects.nonNull(vo.getTimes()) ? vo.getTimes() : 0);
                userAmount.setSubsidy(BigDecimal.ZERO);
            } else {
                userAmount.setAmount(Objects.nonNull(vo.getAmount()) ? vo.getAmount().add(userAmount.getAmount()) : userAmount.getAmount());
                userAmount.setTimes(Objects.nonNull(vo.getTimes()) ? vo.getTimes() + userAmount.getTimes() : userAmount.getTimes());
            }
            userAmountMpService.saveOrUpdate(userAmount);
        }

        return BaseResult.success();
    }

}

