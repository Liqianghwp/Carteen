package com.diandong.domain.dto;

import com.baomidou.mybatisplus.annotation.TableField;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 菜品添加剂信息DTO实体类
 *
 * @author YuLiu
 * @date 2022-05-24
 */
@Data
@ApiModel("菜品添加剂信息DTO实体类")
public class DishesAdditiveDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 编号
     */
    @ApiModelProperty(value = "编号")
    private Long id;
    /**
     * 菜品id
     */
    @ApiModelProperty(value = "菜品id")
    private Long dishesId;
    /**
     * 添加剂名称
     */
    @ApiModelProperty(value = "添加剂名称")
    private String additiveName;

    /**
     * 含量
     */
    @ApiModelProperty(value = "含量")
    private String content;

    /**
     * 创建者
     */
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
    @ApiModelProperty(value = "更新者")
    private Long updateBy;

    /**
     * 更新时间
     */
    @ApiModelProperty(value = "更新时间")
    private LocalDateTime updateTime;

}