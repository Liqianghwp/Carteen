package com.diandong.domain.vo;

import com.diandong.configuration.Insert;
import com.diandong.configuration.Update;
import com.diandong.domain.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.io.Serializable;

/**
 * 配料管理VO实体类
 *
 * @author YuLiu
 * @date 2022-05-17
 */
@Data
@ApiModel("配料管理VO实体类")
public class IngredientsVO extends BaseEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 编号
     */
    @NotNull(groups = {Update.class})
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


}