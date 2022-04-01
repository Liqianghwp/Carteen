package com.diandong.service.impl;

import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.baomidou.mybatisplus.core.toolkit.ObjectUtils;
import com.diandong.configuration.CommonServiceImpl;
import com.diandong.domain.po.NutritionAdvicePO;
import com.diandong.domain.vo.IntakeAnalysisResponseVO;
import com.diandong.domain.vo.IntakeAnalysisVO;
import com.diandong.domain.vo.NutritionAdviceVO;
import com.diandong.mapper.NutritionAdviceMapper;
import com.diandong.mapstruct.NutritionAdviceMsMapper;
import com.diandong.service.NutritionAdviceMpService;
import com.ruoyi.common.core.domain.BaseResult;
import com.ruoyi.common.core.domain.model.LoginUser;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicReference;
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

    @Override
    public BaseResult inputNutritionAdvice(List<NutritionAdviceVO> naList, LoginUser loginUser) throws Exception {

        Boolean result = false;
        for (NutritionAdviceVO nutritionAdviceVO : naList) {


            NutritionAdvicePO po = NutritionAdviceMsMapper.INSTANCE.vo2po(nutritionAdviceVO);

            po.setCreateBy(loginUser.getUserId());
            po.setCreateName(loginUser.getUsername());

            result = save(po);

            if (!result) {
                throw new Exception("添加失败");
            }
        }
        return BaseResult.successMsg("添加成功");
    }

    @Override
    public BaseResult intakeAnalysis(IntakeAnalysisVO vo, LoginUser loginUser) {

//        开始时间
        LocalDateTime startTime = vo.getStartTime();
//        结束时间
        LocalDateTime endTime = vo.getEndTime();
//        获取查询时间的最小值和最大值
        LocalDateTime startMinTime = LocalDateTime.of(startTime.toLocalDate(), LocalTime.MIN);
        LocalDateTime endMaxTime = LocalDateTime.of(endTime.toLocalDate(), LocalTime.MAX);

        DateTimeFormatter dayDtf = DateTimeFormatter.ISO_LOCAL_DATE;

        List<NutritionAdvicePO> list = lambdaQuery()
                .eq(ObjectUtils.isNotEmpty(vo.getMealTimesId()), NutritionAdvicePO::getMealTimesId, vo.getMealTimesId())
                .eq(NutritionAdvicePO::getNutritionalId, vo.getNutritionId())
                .eq(NutritionAdvicePO::getCreateBy, loginUser.getUserId())
                .between(NutritionAdvicePO::getCreateTime, startMinTime, endMaxTime)
                .list();


        IntakeAnalysisResponseVO intakeAnalysisResponseVO = new IntakeAnalysisResponseVO();

        Map<String, Double> inputMap = new HashMap<>();
        Map<String, Double> suggestionMap = new HashMap<>();

        while (startMinTime.isBefore(endMaxTime)) {
            String formatDay = startMinTime.format(dayDtf);
            List<NutritionAdvicePO> collect = list.stream().filter(nutritionAdvicePO -> nutritionAdvicePO.getCreateTime().format(dayDtf).equals(formatDay)).collect(Collectors.toList());

            if (CollectionUtils.isEmpty(collect)) {
                inputMap.put(formatDay, 0D);
            } else {
                AtomicReference<Double> total = new AtomicReference<>(0d);
                collect.forEach(nutritionAdvicePO -> {
                    total.updateAndGet(v -> v + nutritionAdvicePO.getNumber());
                });
                inputMap.put(formatDay, total.get());
            }

            suggestionMap.put(formatDay, 0d);
            startMinTime = startMinTime.plusDays(1L);
        }
        intakeAnalysisResponseVO.setInputMap(inputMap);
        intakeAnalysisResponseVO.setSuggestionMap(suggestionMap);
        return BaseResult.success(intakeAnalysisResponseVO);
    }

    @Override
    public BaseResult getNutritionAdvice(Long mealTimesId, LoginUser loginUser) {

        LocalDateTime now = LocalDateTime.now();

        //        获取当天的最小时间和最大时间
        LocalDateTime nowMinTime = LocalDateTime.of(now.toLocalDate(), LocalTime.MIN);
        LocalDateTime nowMaxTime = LocalDateTime.of(now.toLocalDate(), LocalTime.MAX);

        List<NutritionAdvicePO> list = lambdaQuery()
                .eq(Objects.nonNull(mealTimesId), NutritionAdvicePO::getMealTimesId, mealTimesId)
                .eq(NutritionAdvicePO::getCreateBy, loginUser.getUserId())
                .between(NutritionAdvicePO::getCreateTime, nowMinTime, nowMaxTime)
                .list();
        
        return BaseResult.success(list);
    }
}
