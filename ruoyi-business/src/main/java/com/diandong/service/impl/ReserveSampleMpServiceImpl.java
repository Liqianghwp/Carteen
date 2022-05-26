package com.diandong.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.diandong.configuration.CommonServiceImpl;
import com.diandong.domain.dto.ReserveSampleDTO;
import com.diandong.domain.po.CanteenPO;
import com.diandong.domain.po.ReserveSamplePO;
import com.diandong.mapper.ReserveSampleMapper;
import com.diandong.mapstruct.ReserveSampleMsMapper;
import com.diandong.service.CanteenMpService;
import com.diandong.service.ReserveSampleMpService;
import com.ruoyi.common.core.domain.entity.SysDictData;
import com.ruoyi.system.service.ISysDictDataService;
import org.springframework.beans.BeanUtils;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * 预留样品service实现类
 *
 * @author YuLiu
 * @date 2022-05-11
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class ReserveSampleMpServiceImpl extends CommonServiceImpl<ReserveSampleMapper, ReserveSamplePO>
        implements ReserveSampleMpService {


    @Resource
    private CanteenMpService canteenMpService;
    @Resource
    private ISysDictDataService dictDataService;


    @Override
    public Page<ReserveSampleDTO> resetPage(Page<ReserveSamplePO> page) {

        Page<ReserveSampleDTO> result = new Page<>();
        List<ReserveSamplePO> records = page.getRecords();

        List<ReserveSampleDTO> dtoList = new ArrayList<>();
        BeanUtils.copyProperties(page, result);

//        for (ReserveSamplePO record : records) {
//            ReserveSampleDTO reserveSampleDTO = ReserveSampleMsMapper.INSTANCE.po2dto(record);
//            CanteenPO canteen = canteenMpService.getById(record.getReserveCanteenId());
//            reserveSampleDTO.setReserveCanteenName(Objects.nonNull(canteen) ? canteen.getCanteenName() : null);
//
//            SysDictData mealTimes = dictDataService.lambdaQuery().eq(SysDictData::getDictCode,record.getMealTimes()).one();
//            reserveSampleDTO.setMealTimesName(Objects.nonNull(mealTimes) ? mealTimes.getDictLabel() : null);
//            SysDictData location = dictDataService.lambdaQuery().eq(SysDictData::getDictCode,record.getStorageLocation()).one();
//            reserveSampleDTO.setStorageLocationName(Objects.nonNull(location) ? location.getDictLabel() : null);
//
//            dtoList.add(reserveSampleDTO);
//        }

        po2dto(dtoList, records);

        result.setRecords(dtoList);

        return result;
    }

    @Override
    public List<ReserveSampleDTO> changeDTO(List<ReserveSamplePO> list) {
        List<ReserveSampleDTO> dtoList = new ArrayList<>();
//        转换并且更新值
        po2dto(dtoList, list);
        return dtoList;
    }


    private void po2dto(List<ReserveSampleDTO> dtoList, List<ReserveSamplePO> poList) {
        for (ReserveSamplePO record : poList) {
            ReserveSampleDTO reserveSampleDTO = ReserveSampleMsMapper.INSTANCE.po2dto(record);
            CanteenPO canteen = canteenMpService.getById(record.getReserveCanteenId());
            reserveSampleDTO.setReserveCanteenName(Objects.nonNull(canteen) ? canteen.getCanteenName() : null);

            SysDictData mealTimes = dictDataService.lambdaQuery().eq(SysDictData::getDictCode, record.getMealTimes()).one();
            reserveSampleDTO.setMealTimesName(Objects.nonNull(mealTimes) ? mealTimes.getDictLabel() : null);
            SysDictData location = dictDataService.lambdaQuery().eq(SysDictData::getDictCode, record.getStorageLocation()).one();
            reserveSampleDTO.setStorageLocationName(Objects.nonNull(location) ? location.getDictLabel() : null);

            dtoList.add(reserveSampleDTO);
        }
    }
}
