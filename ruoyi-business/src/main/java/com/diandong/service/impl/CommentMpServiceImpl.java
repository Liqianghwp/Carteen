package com.diandong.service.impl;

import com.diandong.configuration.CommonServiceImpl;
import com.diandong.domain.po.CommentPO;
import com.diandong.mapper.CommentMapper;
import com.diandong.service.CommentMpService;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

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

}
