package com.diandong.domain.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @Classname DictDishTypeVO
 * @Description 菜品类别导出入参
 * @Date 2022/5/16 17:35
 * @Created by YuLiu
 */

@Data
@ApiModel(value = "菜品类别导出入参")
public class DictDishTypeVO implements Serializable {

    private static final long serialVersionUID = 8506919558600920665L;

    @ApiModelProperty(value = "菜品类别名称")
    private String dishTypeName;

    /**
     *
     */
    @ApiModelProperty(value = "勾选要导出的id集合")
    private List<Long> ids;


}
