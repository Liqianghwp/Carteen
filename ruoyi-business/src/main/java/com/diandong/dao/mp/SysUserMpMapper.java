package com.diandong.dao.mp;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.diandong.pojo.po.SysUser;

/**
* @Entity com.diandong.pojo.po.SysUser
*/
public interface SysUserMpMapper extends BaseMapper<SysUser> {
    int countTest();
}
