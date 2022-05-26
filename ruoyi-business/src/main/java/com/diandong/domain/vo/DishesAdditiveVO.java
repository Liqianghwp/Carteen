package com.diandong.domain.vo;

import com.baomidou.mybatisplus.annotation.TableField;
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
 * 菜品添加剂信息VO实体类
 *
 * @author YuLiu
 * @date 2022-05-24
 */
@Data
@ApiModel("菜品添加剂信息VO实体类")
public class DishesAdditiveVO extends BaseEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 编号
     */
    @NotNull(groups = {Update.class})
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


}