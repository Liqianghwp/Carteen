package com.diandong.service;

import com.diandong.domain.vo.BackstageRechargeVO;
import com.diandong.domain.vo.PhysicalCardVO;
import com.diandong.domain.vo.SysUserVO;
import com.ruoyi.common.core.domain.BaseResult;

/**
 * @Classname IUserManagementService
 * @Description 用户管理Service
 * @Date 2022/5/31 14:53
 * @Created by YuLiu
 */
public interface IUserManagementService {

    /**
     * 根据用户id查看用户信息
     *
     * @param userId 用户id
     * @return
     */
    BaseResult getById(String userId);

    /**
     * 用户管理-新增用户
     *
     * @param vo 用户信息
     * @return
     */
    Boolean saveUser(SysUserVO vo);

    /**
     * 用户管理-编辑修改用户
     *
     * @param vo 用户信息
     * @return
     */
    Boolean updateUser(SysUserVO vo);

    /**
     * 绑定卡片
     *
     * @param vo 实体卡信息
     * @return
     */
    BaseResult bindCard(PhysicalCardVO vo);

    /**
     * 后台充值
     * @param vo    充值实体类
     * @return
     */
    BaseResult recharge(BackstageRechargeVO vo);
}
