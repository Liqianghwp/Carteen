package com.diandong.domain.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 系统配置 用于富文本设置VO实体类
 *
 * @author YuLiu
 * @date 2022-05-11
 */
@ApiModel(value = "富文本设置")
@Data
public class BusinessConfigVO implements Serializable {
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


}