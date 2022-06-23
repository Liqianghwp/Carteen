package com.diandong.service.impl;

import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.baomidou.mybatisplus.core.toolkit.ObjectUtils;
import com.diandong.configuration.CommonServiceImpl;
import com.diandong.constant.Constants;
import com.diandong.domain.po.*;
import com.diandong.domain.vo.IntakeAnalysisResponseVO;
import com.diandong.domain.vo.IntakeAnalysisVO;
import com.diandong.domain.vo.NutritionAdviceVO;
import com.diandong.mapper.NutritionAdviceMapper;
import com.diandong.mapstruct.NutritionAdviceMsMapper;
import com.diandong.service.*;
import com.ruoyi.common.core.domain.BaseResult;
import com.ruoyi.common.utils.SecurityUtils;
import com.ruoyi.common.utils.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

/**
 * service实现类
 *
 * @author YuLiu
 * @date 2022-03-31
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class NutritionAdviceMpServiceImpl extends CommonServiceImpl<NutritionAdviceMapper, NutritionAdvicePO>
        implements NutritionAdviceMpService {

    @Resource
    private OrderMpService orderMpService;
    @Resource
    private OrderDetailMpService orderDetailMpService;
    @Resource
    private DishesNutritionMpService dishesNutritionMpService;
    @Resource
    private BizDictMpService dictMpService;

    @Override
    public BaseResult inputNutritionAdvice(List<NutritionAdviceVO> naList) {

        List<NutritionAdvicePO> list = new ArrayList<>();


        for (NutritionAdviceVO nutritionAdviceVO : naList) {
            NutritionAdvicePO nutritionAdvice = lambdaQuery()
                    .eq(NutritionAdvicePO::getMealTimesId, nutritionAdviceVO.getMealTimesId())
                    .eq(NutritionAdvicePO::getNutritionalId, nutritionAdviceVO.getNutritionalId())
                    .eq(NutritionAdvicePO::getCreateBy, SecurityUtils.getUserId())
                    .eq(NutritionAdvicePO::getDelFlag, Constants.DEL_NO)
                    .last(Constants.limit).one();

            if (Objects.nonNull(nutritionAdvice)) {
                nutritionAdvice.setUnit(nutritionAdviceVO.getUnit());
                nutritionAdvice.setNumber(nutritionAdviceVO.getNumber());
            } else {
                nutritionAdvice = NutritionAdviceMsMapper.INSTANCE.vo2po(nutritionAdviceVO);
                nutritionAdvice.setUserId(SecurityUtils.getUserId());
                if (StringUtils.isBlank(nutritionAdviceVO.getNutritionalName())) {
                    BizDictPO dict = dictMpService.getById(nutritionAdvice.getNutritionalId());
                    nutritionAdvice.setNutritionalName(Objects.nonNull(dict) ? dict.getDictLabel() : null);
                }
                if (StringUtils.isBlank(nutritionAdviceVO.getMealTimesName())) {
                    BizDictPO mealDict = dictMpService.getById(nutritionAdviceVO.getMealTimesId());
                    nutritionAdvice.setMealTimesName(Objects.nonNull(mealDict) ? mealDict.getDictLabel() : null);
                }
            }

            list.add(nutritionAdvice);
        }
        saveOrUpdateBatch(list);
        return BaseResult.successMsg("添加成功");
    }

    @Override
    public BaseResult intakeAnalysis(IntakeAnalysisVO vo) {

//        开始时间
        LocalDateTime startTime = vo.getStartTime();
//        结束时间
        LocalDateTime endTime = vo.getEndTime();

        DateTimeFormatter dayDtf = DateTimeFormatter.ISO_LOCAL_DATE;

        List<NutritionAdvicePO> list = lambdaQuery()
                .eq(ObjectUtils.isNotEmpty(vo.getMealTimesId()), NutritionAdvicePO::getMealTimesId, vo.getMealTimesId())
                .eq(NutritionAdvicePO::getNutritionalId, vo.getNutritionId())
                .eq(NutritionAdvicePO::getCreateBy, SecurityUtils.getUserId())
                .list();


        IntakeAnalysisResponseVO intakeAnalysisResponseVO = new IntakeAnalysisResponseVO();

        Map<String, Double> inputMap = new HashMap<>();
        Map<String, Double> suggestionMap = new HashMap<>();


        Double ownSum = CollectionUtils.isNotEmpty(list) ? list.stream().mapToDouble(NutritionAdvicePO::getNumber).sum() : 0d;

        while (startTime.isBefore(endTime) || startTime.equals(endTime)) {
            String formatDay = startTime.format(dayDtf);

            inputMap.put(formatDay, ownSum);

//            查询当天订单吃饭记录
            LocalDateTime minDateTime = LocalDateTime.of(startTime.toLocalDate(), LocalTime.MIN);
            LocalDateTime maxDateTime = LocalDateTime.of(startTime.toLocalDate(), LocalTime.MAX);
            List<OrderPO> orderList = orderMpService.lambdaQuery()
                    .between(OrderPO::getOrderTime, minDateTime, maxDateTime)
                    .eq(OrderPO::getCreateBy, SecurityUtils.getUserId())
                    .eq(OrderPO::getDelFlag, Constants.DEL_NO)
                    .list();
            Double totalSum = 0d;
            if (CollectionUtils.isNotEmpty(orderList)) {

                List<Long> orderIdList = orderList.stream().map(OrderPO::getId).collect(Collectors.toList());
                List<OrderDetailPO> orderDetailList = orderDetailMpService.lambdaQuery()
                        .in(OrderDetailPO::getOrderId, orderIdList)
                        .eq(Objects.nonNull(vo.getMealTimesId()), OrderDetailPO::getMealTimesId, vo.getMealTimesId())
                        .eq(OrderDetailPO::getDelFlag, Constants.DEL_NO)
                        .list();

//            菜品id集合


                Map<Long, List<OrderDetailPO>> dishesMap = orderDetailList.stream().collect(Collectors.groupingBy(OrderDetailPO::getDishesId));


                for (Map.Entry<Long, List<OrderDetailPO>> entry : dishesMap.entrySet()) {
                    Long dishesId = entry.getKey();
                    List<OrderDetailPO> value = entry.getValue();
                    int count = value.stream().mapToInt(OrderDetailPO::getDishesCount).sum();


                    DishesNutritionPO dishesNutrition = dishesNutritionMpService.lambdaQuery()
                            .eq(DishesNutritionPO::getCanteenId, SecurityUtils.getCanteenId())
                            .eq(DishesNutritionPO::getDishesId, dishesId)
                            .eq(DishesNutritionPO::getNutritionId, vo.getNutritionId())
                            .eq(DishesNutritionPO::getDelFlag, Constants.DEL_NO)
                            .last(Constants.limit).one();

                    totalSum = totalSum + dishesNutrition.getNumber() * count;

                }

//                List<Long> dishesIds = orderDetailList.stream().map(OrderDetailPO::getDishesId).collect(Collectors.toList());
//
//                List<DishesNutritionPO> dishesNutritionList = dishesNutritionMpService.lambdaQuery()
//                        .eq(DishesNutritionPO::getCanteenId, SecurityUtils.getCanteenId())
//                        .in(DishesNutritionPO::getDishesId, dishesIds)
//                        .eq(DishesNutritionPO::getNutritionId, vo.getNutritionId())
//                        .eq(DishesNutritionPO::getDelFlag, Constants.DEL_NO)
//                        .list();
//
//                if (CollectionUtils.isNotEmpty(dishesNutritionList)) {
//                    totalSum = dishesNutritionList.stream().mapToDouble(DishesNutritionPO::getNumber).sum();
//                }
            }
            suggestionMap.put(formatDay, totalSum);

//            日期+1
            startTime = startTime.plusDays(1L);
        }
        intakeAnalysisResponseVO.setInputMap(inputMap);
        intakeAnalysisResponseVO.setSuggestionMap(suggestionMap);
        return BaseResult.success(intakeAnalysisResponseVO);
    }

    @Override
    public BaseResult getNutritionAdvice(Long mealTimesId) {

        LocalDateTime now = LocalDateTime.now();

        //        获取当天的最小时间和最大时间
        LocalDateTime nowMinTime = LocalDateTime.of(now.toLocalDate(), LocalTime.MIN);
        LocalDateTime nowMaxTime = LocalDateTime.of(now.toLocalDate(), LocalTime.MAX);

        List<NutritionAdvicePO> list = lambdaQuery()
                .eq(Objects.nonNull(mealTimesId), NutritionAdvicePO::getMealTimesId, mealTimesId)
                .between(NutritionAdvicePO::getCreateTime, nowMinTime, nowMaxTime)
                .list();

        return BaseResult.success(list);
    }
}
