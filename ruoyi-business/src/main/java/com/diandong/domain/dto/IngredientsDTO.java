package com.diandong.domain.dto;

import com.diandong.domain.po.IngredientsDetailPO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

/**
 * 配料管理DTO实体类
 *
 * @author YuLiu
 * @date 2022-05-17
 */
@Data
@ApiModel("配料管理DTO实体类")
public class IngredientsDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 编号
     */
    @ApiModelProperty(value = "编号")
    private Long id;

    /**
     * 菜品类别id
     */
    @ApiModelProperty(value = "菜品类别id")
    private Long dishTypeId;

    /**
     * 菜品类别名称
     */
    @ApiModelProperty(value = "菜品类别名称")
    private String dishTypeName;

    /**
     * 菜品id
     */
    @ApiModelProperty(value = "菜品id")
    private Long dishId;

    /**
     * 菜品名称
     */
    @ApiModelProperty(value = "菜品名称")
    private String dishName;

    /**
     * 备注
     */
    @ApiModelProperty(value = "备注")
    private String remark;

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

    @ApiModelProperty(value = "主料")
    private List<IngredientsDetailPO> zio;

    @ApiModelProperty(value = "辅料")
    private List<IngredientsDetailPO> zic;
}