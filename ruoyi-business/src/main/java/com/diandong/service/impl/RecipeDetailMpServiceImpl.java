package com.diandong.service.impl;

import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.diandong.configuration.CommonServiceImpl;
import com.diandong.constant.Constants;
import com.diandong.domain.dto.RecipePieSituationDayDTO;
import com.diandong.domain.po.RecipeDetailPO;
import com.diandong.domain.po.RecipePO;
import com.diandong.domain.vo.RecipePieSituationVO;
import com.diandong.mapper.RecipeDetailMapper;
import com.diandong.service.RecipeDetailMpService;
import com.diandong.service.RecipeMpService;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAdjuster;
import java.time.temporal.TemporalAdjusters;
import java.util.*;
import java.util.stream.Collectors;

/**
 * service实现类
 *
 * @author YuLiu
 * @date 2022-04-02
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class RecipeDetailMpServiceImpl extends CommonServiceImpl<RecipeDetailMapper, RecipeDetailPO>
        implements RecipeDetailMpService {

    //    食谱Service
    @Resource
    private RecipeMpService recipeMpService;

    @Override
    public void pieSituation(RecipePieSituationVO pieSituationVO) {

        Long dishId = pieSituationVO.getDishId();

        LocalDate date = pieSituationVO.getDate();
//        记录当月的次数
//        LocalDate monthFirstDay = date.with(TemporalAdjusters.firstDayOfMonth());
//        LocalDate monthLastDay = date.with(TemporalAdjusters.lastDayOfMonth());
//        List<RecipePO> monthList = recipeMpService.lambdaQuery()
//                .between(RecipePO::getRecipeDate, monthFirstDay, monthLastDay)
//                .eq(RecipePO::getDelFlag, Constants.DEL_NO)
//                .list();

        LocalDateTime monthFirstDay = date.with(TemporalAdjusters.firstDayOfMonth()).atStartOfDay();
        LocalDateTime monthLastDay = date.with(TemporalAdjusters.firstDayOfMonth()).atTime(23, 59, 59);

        RecipePieSituationDayDTO monthDayDto = getRecipeDetailList(dishId, monthFirstDay, monthLastDay);


//        记录当前季度的次数
//        当前时间季度的开始日期&结束日期
        Month month = Month.of(date.getMonth().firstMonthOfQuarter().getValue());
        LocalDateTime quarterFirstDay = LocalDateTime.of(LocalDate.of(date.getYear(), month, 1), LocalTime.MIN);
        month = month.plus(2L);
        LocalDateTime quarterLastDay = LocalDateTime.of(LocalDate.of(date.getYear(), month, month.length(date.isLeapYear())), LocalTime.MAX);

//        List<RecipePO> quarterLastList = recipeMpService.lambdaQuery()
//                .between(RecipePO::getRecipeDate, monthFirstDay, monthLastDay)
//                .eq(RecipePO::getDelFlag, Constants.DEL_NO)
//                .list();

        RecipePieSituationDayDTO quarterDayDto = getRecipeDetailList(dishId, monthFirstDay, monthLastDay);

    }


    /**
     * 获取菜品出现日期
     *
     * @param dishId    菜品id
     * @param firstTime 开始时间
     * @param lastTime  结束时间
     * @return
     */
    private RecipePieSituationDayDTO getRecipeDetailList(Long dishId, LocalDateTime firstTime, LocalDateTime lastTime) {
        RecipePieSituationDayDTO recipePieSituationDayDTO = new RecipePieSituationDayDTO();

        Set<String> dayList = new HashSet<>();

        List<RecipePO> list = recipeMpService.lambdaQuery()
                .between(RecipePO::getRecipeDate, firstTime, lastTime)
                .eq(RecipePO::getDelFlag, Constants.DEL_NO)
                .list();

        if (CollectionUtils.isNotEmpty(list)) {
            //        获取菜谱id
            List<Long> collect = list.stream().map(RecipePO::getId).collect(Collectors.toList());

//            获取菜品详情信息
            List<RecipeDetailPO> dishList = lambdaQuery()
                    .in(RecipeDetailPO::getRecipeId, collect)
                    .eq(RecipeDetailPO::getDishesId, dishId)
                    .eq(RecipeDetailPO::getDelFlag, Constants.DEL_NO)
                    .list();
            List<Long> recipeList = dishList.stream().map(RecipeDetailPO::getRecipeId).collect(Collectors.toList());
            if (CollectionUtils.isNotEmpty(recipeList)) {
                List<RecipePO> collect1 = list.stream().filter(recipePO -> recipeList.contains(recipePO.getId())).collect(Collectors.toList());

                List<LocalDate> collect2 = collect1.stream().map(RecipePO::getRecipeDate).collect(Collectors.toList());

                collect2.forEach(localDate -> dayList.add(localDate.format(DateTimeFormatter.ISO_DATE)));
            }


            recipePieSituationDayDTO.setCount(dishList.size());

        }

        recipePieSituationDayDTO.setList(Arrays.asList(dayList.toArray(new String[0])));
        return recipePieSituationDayDTO;
    }


}
