package com.diandong.service.impl;

import com.diandong.configuration.CommonServiceImpl;
import com.diandong.domain.po.IngredientsDetailPO;
import com.diandong.mapper.IngredientsDetailMapper;
import com.diandong.service.IngredientsDetailMpService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 配料管理详情service实现类
 *
 * @author YuLiu
 * @date 2022-05-17
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class IngredientsDetailMpServiceImpl extends CommonServiceImpl<IngredientsDetailMapper, IngredientsDetailPO>
        implements IngredientsDetailMpService {

    @Override
    public Long echoMessage(long id) {

        return baseMapper.delDetailById(id);
    }
}
