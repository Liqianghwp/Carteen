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
 * 配料管理详情VO实体类
 *
 * @author YuLiu
 * @date 2022-05-17
 */
@Data
@ApiModel("配料管理详情VO实体类")
public class IngredientsDetailVO extends BaseEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 编号
     */
    @NotNull(groups = {Update.class})
    @ApiModelProperty(value = "编号")
    private Long id;

    /**
     * 父级id
     */
    @ApiModelProperty(value = "父级id")
    private Long ingredientsId;

    /**
     * 配料类型 0：主料；1：辅料
     */
    @ApiModelProperty(value = "配料类型 0：主料；1：辅料")
    private String type;

    /**
     * 原材料id
     */
    @ApiModelProperty(value = "原材料id")
    private Long rawMaterialId;

    /**
     * 原材料名称
     */
    @ApiModelProperty(value = "原材料名称")
    private String rawMaterialName;

    /**
     * 重量
     */
    @ApiModelProperty(value = "重量")
    private Double number;


}