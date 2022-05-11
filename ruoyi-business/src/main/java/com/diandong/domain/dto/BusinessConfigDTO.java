package com.diandong.domain.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 系统配置 用于富文本设置DTO实体类
 *
 * @author YuLiu
 * @date 2022-05-11
 */
@Data
@ApiModel("系统配置 用于富文本设置DTO实体类")
public class BusinessConfigDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 编号
     */
    @ApiModelProperty(value = "编号")
    private Long id;

    /**
     * 配置名称
     */
    @ApiModelProperty(value = "配置名称")
    private String configName;

    /**
     * 配置内容 text文本格式
     */
    @ApiModelProperty(value = "配置内容 text文本格式")
    private String configValue;

    /**
     * 数据状态 停用启用状态
     */
    @ApiModelProperty(value = "数据状态 停用启用状态")
    private Integer dataState;

    /**
     * 创建人id
     */
    @ApiModelProperty(value = "创建人id")
    private Long createBy;

    /**
     * 创建时间
     */
    @ApiModelProperty(value = "创建时间")
    private LocalDateTime createTime;

    /**
     * 更新人id
     */
    @ApiModelProperty(value = "更新人id")
    private Long updateBy;

    /**
     * 更新时间
     */
    @ApiModelProperty(value = "更新时间")
    private LocalDateTime updateTime;

}