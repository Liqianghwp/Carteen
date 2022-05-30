package com.diandong.domain.dto;

import com.ruoyi.common.core.domain.entity.SysUser;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author Larry
 * @date 2022/1/30 0030 14:46
 * @description
 */
@Data
@ApiModel("用户,鉴权token")
public class UserAndTokenDto {
    @ApiModelProperty("token")
    private String token;
    @ApiModelProperty("用户实体")
    private SysUser user;
}
