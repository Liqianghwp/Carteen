package com.diandong.service.impl;

import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.diandong.configuration.CommonServiceImpl;
import com.diandong.constant.Constants;
import com.diandong.domain.po.GroupManagementPO;
import com.diandong.domain.vo.GroupManagementVO;
import com.diandong.mapper.GroupManagementMapper;
import com.diandong.mapstruct.GroupManagementMsMapper;
import com.diandong.service.GroupManagementMpService;
import com.ruoyi.common.core.domain.entity.SysDept;
import com.ruoyi.common.exception.ServiceException;
import com.ruoyi.system.constant.DeptConstants;
import com.ruoyi.system.service.ISysDeptService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

/**
 * service实现类
 *
 * @author YuLiu
 * @date 2022-03-29
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class GroupManagementMpServiceImpl extends CommonServiceImpl<GroupManagementMapper, GroupManagementPO>
        implements GroupManagementMpService {

    @Resource
    private ISysDeptService deptService;
//    @Resource
//    private ISysUserService userService;


    @Override
    public Boolean saveGroupManagement(GroupManagementVO vo) {

        List<GroupManagementPO> list = lambdaQuery().eq(GroupManagementPO::getGroupName, vo.getGroupName()).eq(GroupManagementPO::getDelFlag, Constants.DEL_NO).list();
        if(CollectionUtils.isNotEmpty(list)){
            throw new ServiceException("集团名称重复");
        }


//        如果保存成功则在若依下面添加一个部门

        SysDept sysDept = new SysDept();
        sysDept.setDeptName(vo.getGroupName());
        sysDept.setParentId(DeptConstants.GROUP);
        deptService.insertDept(sysDept);


        SysDept dept = deptService.getOneByDeptName(sysDept);

        GroupManagementPO po = GroupManagementMsMapper.INSTANCE.vo2po(vo);
//        设置部门信息
        po.setDeptId(dept.getDeptId());
        return save(po);
    }
}
