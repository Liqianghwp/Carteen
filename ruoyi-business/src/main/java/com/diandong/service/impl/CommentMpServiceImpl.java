package com.diandong.service.impl;

import cn.hutool.core.util.NumberUtil;
import com.diandong.configuration.CommonServiceImpl;
import com.diandong.constant.Constants;
import com.diandong.domain.po.CommentPO;
import com.diandong.domain.po.DishesEvaluationsRecordsPO;
import com.diandong.domain.po.OrderDetailPO;
import com.diandong.domain.po.OrderPO;
import com.diandong.domain.vo.CommentVO;
import com.diandong.mapper.CommentMapper;
import com.diandong.mapstruct.CommentMsMapper;
import com.diandong.service.CommentMpService;
import com.diandong.service.DishesEvaluationsRecordsMpService;
import com.diandong.service.OrderDetailMpService;
import com.diandong.service.OrderMpService;
import com.ruoyi.common.core.domain.BaseResult;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * service实现类
 *
 * @author YuLiu
 * @date 2022-03-29
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class CommentMpServiceImpl extends CommonServiceImpl<CommentMapper, CommentPO>
        implements CommentMpService {

    @Resource
    private OrderMpService orderMpService;
    @Resource
    private OrderDetailMpService orderDetailMpService;
    @Resource
    private DishesEvaluationsRecordsMpService dishesEvaluationsRecordsMpService;


    @Override
    public BaseResult saveOrderEvaluation(CommentVO vo) {


        CommentPO commentPO = CommentMsMapper.INSTANCE.vo2po(vo);
        save(commentPO);


        OrderPO order = orderMpService.getById(commentPO.getOrderId());

        List<String> deliciousDishesList = Arrays.asList(vo.getDeliciousDishes().split(","));
        List<String> undeliciousDishesList = Arrays.asList(vo.getUndeliciousDishes().split(","));


        Map<String, Long> deliciousDishesMap = deliciousDishesList.stream().collect(Collectors.groupingBy(Function.identity(), Collectors.counting()));
        Map<String, Long> undeliciousDishesMap = undeliciousDishesList.stream().collect(Collectors.groupingBy(Function.identity(), Collectors.counting()));


        for (Map.Entry<String, Long> entry : deliciousDishesMap.entrySet()) {

            DishesEvaluationsRecordsPO dishesEvaluationsRecords = dishesEvaluationsRecordsMpService.lambdaQuery()
                    .eq(DishesEvaluationsRecordsPO::getCanteenId, order.getCanteenId())
                    .eq(DishesEvaluationsRecordsPO::getDishesId, entry.getKey())
                    .eq(DishesEvaluationsRecordsPO::getDelFlag, Constants.DEL_NO)
                    .last(Constants.limit).one();


            List<OrderDetailPO> list = orderDetailMpService.lambdaQuery()
                    .eq(OrderDetailPO::getOrderId, order.getId())
                    .eq(OrderDetailPO::getDishesId, entry.getKey())
                    .list();

            int goodNum = list.stream().mapToInt(OrderDetailPO::getDishesCount).sum();

            if (Objects.isNull(dishesEvaluationsRecords)) {
                dishesEvaluationsRecords = new DishesEvaluationsRecordsPO();

                dishesEvaluationsRecords.setCanteenId(order.getCanteenId());
                dishesEvaluationsRecords.setDishesId(Long.valueOf(entry.getKey()));
                dishesEvaluationsRecords.setGoodNum(goodNum);
                dishesEvaluationsRecords.setBadNum(0);
                dishesEvaluationsRecords.setPraise(1.00);
            } else {
                dishesEvaluationsRecords.setGoodNum(dishesEvaluationsRecords.getGoodNum() + goodNum);
                dishesEvaluationsRecords.setPraise(NumberUtil.div(dishesEvaluationsRecords.getGoodNum().intValue(), dishesEvaluationsRecords.getGoodNum() + dishesEvaluationsRecords.getBadNum()));
            }
            dishesEvaluationsRecordsMpService.saveOrUpdate(dishesEvaluationsRecords);
        }


        for (Map.Entry<String, Long> entry : undeliciousDishesMap.entrySet()) {

            DishesEvaluationsRecordsPO dishesEvaluationsRecords = dishesEvaluationsRecordsMpService.lambdaQuery()
                    .eq(DishesEvaluationsRecordsPO::getCanteenId, order.getCanteenId())
                    .eq(DishesEvaluationsRecordsPO::getDishesId, entry.getKey())
                    .eq(DishesEvaluationsRecordsPO::getDelFlag, Constants.DEL_NO)
                    .last(Constants.limit).one();

            List<OrderDetailPO> list = orderDetailMpService.lambdaQuery()
                    .eq(OrderDetailPO::getOrderId, order.getId())
                    .eq(OrderDetailPO::getDishesId, entry.getKey())
                    .list();
            int badNum = list.stream().mapToInt(OrderDetailPO::getDishesCount).sum();

            if (Objects.isNull(dishesEvaluationsRecords)) {
                dishesEvaluationsRecords = new DishesEvaluationsRecordsPO();

                dishesEvaluationsRecords.setCanteenId(order.getCanteenId());
                dishesEvaluationsRecords.setDishesId(Long.valueOf(entry.getKey()));
                dishesEvaluationsRecords.setGoodNum(0);
                dishesEvaluationsRecords.setBadNum(badNum);
                dishesEvaluationsRecords.setPraise(0.00);
            } else {
                dishesEvaluationsRecords.setBadNum(dishesEvaluationsRecords.getBadNum() + badNum);
                dishesEvaluationsRecords.setPraise(NumberUtil.div(dishesEvaluationsRecords.getGoodNum().intValue(), dishesEvaluationsRecords.getGoodNum() + dishesEvaluationsRecords.getBadNum()));
            }
            dishesEvaluationsRecordsMpService.saveOrUpdate(dishesEvaluationsRecords);
        }


        return BaseResult.success();
    }
}
