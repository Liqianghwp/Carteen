package com.diandong.domain.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 菜品分类DTO实体类
 *
 * @author YuLiu
 * @date 2022-05-16
 */
@Data
@ApiModel("菜品分类DTO实体类")
public class DishesTypeDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 编号
     */
    @ApiModelProperty(value = "编号")
    private Long id;

    /**
     * 食堂id
     */
    @ApiModelProperty(value = "食堂id")
    private Long canteenId;

    /**
     * 食堂名称
     */
    @ApiModelProperty(value = "食堂名称")
    private String canteenName;

    /**
     * 分类名称
     */
    @ApiModelProperty(value = "分类名称")
    private String typeName;

    /**
     * 排序
     */
    @ApiModelProperty(value = "排序")
    private Integer sort;

    /**
     * 是否显示 （0:不显示，1:显示）
     */
    @ApiModelProperty(value = "是否显示 （0:不显示，1:显示）")
    private Integer isShow;

    /**
     * 类型标签
     */
    @ApiModelProperty(value = "类型标签")
    private String remark;

    /**
     * 是否包装
     */
    @ApiModelProperty(value = "是否包装")
    private Integer isPackage;

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
     * 更新者id
     */
    @ApiModelProperty(value = "更新者id")
    private Long updateBy;

    /**
     * 更新时间
     */
    @ApiModelProperty(value = "更新时间")
    private LocalDateTime updateTime;

}