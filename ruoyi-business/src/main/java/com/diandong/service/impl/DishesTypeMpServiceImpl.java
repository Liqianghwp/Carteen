package com.diandong.service.impl;

import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.diandong.configuration.CommonServiceImpl;
import com.diandong.domain.po.DishesTypePO;
import com.diandong.domain.po.GroupManagementPO;
import com.diandong.domain.vo.DishesTypeVO;
import com.diandong.mapper.DishesTypeMapper;
import com.diandong.mapstruct.DishesTypeMsMapper;
import com.diandong.service.DishesTypeMpService;
import com.diandong.service.GroupManagementMpService;
import com.ruoyi.common.core.domain.BaseResult;
import com.ruoyi.common.core.domain.entity.SysDept;
import com.ruoyi.common.utils.SecurityUtils;
import com.ruoyi.system.constant.DeptConstants;
import com.ruoyi.system.service.ISysDeptService;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Objects;

/**
 * service实现类
 *
 * @author YuLiu
 * @date 2022-03-24
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class DishesTypeMpServiceImpl extends CommonServiceImpl<DishesTypeMapper, DishesTypePO>
        implements DishesTypeMpService {


    @Resource
    private GroupManagementMpService groupManagementMpService;
    @Resource
    private ISysDeptService deptService;

    @Override
    public BaseResult saveDishesType(DishesTypeVO vo) {


//        设置创建人信息

        SysDept groupDept = getGroupDept(SecurityUtils.getDeptId());
        if (Objects.isNull(groupDept)) {
            throw new RuntimeException("当前账号无法创建菜品分类");
        }

        GroupManagementPO groupManagement = groupManagementMpService.lambdaQuery().eq(GroupManagementPO::getDeptId, groupDept.getDeptId()).one();
        vo.setGroupId(groupManagement.getId());
        vo.setGroupName(groupManagement.getGroupName());


        List<DishesTypePO> list = lambdaQuery().eq(DishesTypePO::getGroupId, vo.getGroupId()).eq(DishesTypePO::getTypeName, vo.getTypeName()).list();
        if (CollectionUtils.isNotEmpty(list)) {
            return BaseResult.error("该分类已经创建，请勿重复创建");
        }


        if (Objects.isNull(vo.getSort())) {
            DishesTypePO maxSortDishesType = lambdaQuery().eq(DishesTypePO::getGroupId, vo.getGroupId()).orderByDesc(DishesTypePO::getSort).last("limit 1").one();
            vo.setSort(Objects.isNull(maxSortDishesType) ? 0 : maxSortDishesType.getSort() + 1);
        }

        DishesTypePO po = DishesTypeMsMapper.INSTANCE.vo2po(vo);
        boolean result = save(po);
        if (result) {
            return BaseResult.successMsg("添加成功！");
        } else {
            return BaseResult.error("添加失败！");
        }

    }


    /**
     * 查询集团id
     *
     * @param deptId
     * @return
     */
    private SysDept getGroupDept(Long deptId) {

        SysDept dept = null;
        if (deptId == DeptConstants.GROUP) {
            return dept;
        } else {
            dept = deptService.selectDeptById(deptId);
            if (!(dept.getParentId() == DeptConstants.GROUP)) {
                dept = getGroupDept(dept.getParentId());
            }
        }
        return dept;
    }
}
