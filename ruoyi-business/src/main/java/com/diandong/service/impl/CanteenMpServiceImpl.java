package com.diandong.service.impl;

import com.diandong.configuration.CommonServiceImpl;
import com.diandong.constant.Constants;
import com.diandong.domain.po.CanteenPO;
import com.diandong.domain.po.GroupManagementPO;
import com.diandong.domain.vo.CanteenVO;
import com.diandong.mapper.CanteenMapper;
import com.diandong.mapstruct.CanteenMsMapper;
import com.diandong.service.CanteenMpService;
import com.diandong.service.GroupManagementMpService;
import com.ruoyi.common.core.domain.BaseResult;
import com.ruoyi.common.core.domain.entity.SysDept;
import com.ruoyi.common.core.domain.model.LoginUser;
import com.ruoyi.common.utils.SecurityUtils;
import com.ruoyi.system.constant.DeptConstants;
import com.ruoyi.system.service.ISysDeptService;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

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
public class CanteenMpServiceImpl extends CommonServiceImpl<CanteenMapper, CanteenPO>
        implements CanteenMpService {

    @Resource
    private GroupManagementMpService groupManagementMpService;
    @Resource
    private ISysDeptService deptService;

    @Override
    public BaseResult addCanteen(CanteenVO vo) {


        CanteenPO po = CanteenMsMapper.INSTANCE.vo2po(vo);

        LoginUser loginUser = SecurityUtils.getLoginUser();
//        这个可能是集团id
        GroupManagementPO groupManagement = groupManagementMpService.lambdaQuery()
                .eq(GroupManagementPO::getDeptId, loginUser.getDeptId())
                .eq(GroupManagementPO::getDelFlag, Constants.DEL_NO)
                .one();

        List<CanteenPO> list = lambdaQuery()
                .eq(CanteenPO::getPId, groupManagement.getId())
                .eq(CanteenPO::getDelFlag, Constants.DEL_NO)
                .list();
        if (list.size() >= groupManagement.getCanteensAllowed()) {
            return BaseResult.error("食堂已经添加上限，无法在进行添加");
        }

        po.setPId(groupManagement.getId());
        po.setPName(groupManagement.getGroupName());


//        新增食堂时给这集团添加部门信息
        //        如果保存成功则在若依下面添加一个部门

        SysDept sysDept = new SysDept();
        sysDept.setDeptName(vo.getCanteenName());
        sysDept.setParentId(loginUser.getDeptId());
        deptService.insertDept(sysDept);

//        deptService.getOneByDeptName(sysDept);
        po.setDeptId(sysDept.getDeptId());
        boolean result = save(po);
        if (result) {
            return BaseResult.successMsg("添加成功！");
        } else {
            return BaseResult.error("添加失败！");
        }

    }
}
