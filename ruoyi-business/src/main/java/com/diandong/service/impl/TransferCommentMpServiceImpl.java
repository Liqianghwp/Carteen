package com.diandong.service.impl;

import com.diandong.configuration.CommonServiceImpl;
import com.diandong.domain.po.TransferCommentPO;
import com.diandong.mapper.TransferCommentMapper;
import com.diandong.service.TransferCommentMpService;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

/**
 * service实现类
 *
 * @author YuLiu
 * @date 2022-06-08
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class TransferCommentMpServiceImpl extends CommonServiceImpl<TransferCommentMapper, TransferCommentPO>
        implements TransferCommentMpService {

}
