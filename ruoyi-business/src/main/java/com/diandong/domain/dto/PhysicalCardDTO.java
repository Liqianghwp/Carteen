package com.diandong.domain.dto;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 实物卡DTO实体类
 *
 * @author YuLiu
 * @date 2022-05-31
 */
@Data
@ApiModel("实物卡DTO实体类")
public class PhysicalCardDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 编号
     */
    @JsonSerialize(using = ToStringSerializer.class)
    @ApiModelProperty(value = "编号")
    private Long id;

    /**
     * 用户id
     */
    @ApiModelProperty(value = "用户id")
    private Integer userId;

    /**
     * 卡号
     */
    @ApiModelProperty(value = "卡号")
    private String cardNo;

    /**
     * 停用启用 0：停用；1：启用
     */
    @ApiModelProperty(value = "停用启用 0：停用；1：启用")
    private Integer state;

    /**
     * 是否挂失 0：否；1：是
     */
    @ApiModelProperty(value = "是否挂失 0：否；1：是")
    private Integer reportLoss;

    /**
     * 创建者
     */
    @JsonSerialize(using = ToStringSerializer.class)
    @ApiModelProperty(value = "创建者")
    private Long createBy;

    /**
     * 创建时间 默认为当前时间
     */
    @ApiModelProperty(value = "创建时间 默认为当前时间")
    private LocalDateTime createTime;

    /**
     * 更新者
     */
    @JsonSerialize(using = ToStringSerializer.class)
    @ApiModelProperty(value = "更新者")
    private Long updateBy;

    /**
     * 更新时间
     */
    @ApiModelProperty(value = "更新时间")
    private LocalDateTime updateTime;

}