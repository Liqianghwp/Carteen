package com.diandong.domain.dto;

import com.ruoyi.common.annotation.Excel;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @Classname DictDishTypeExportDTO
 * @Description 菜品类别导出实体类
 * @Date 2022/5/16 18:27
 * @Created by YuLiu
 */
@Data
@ApiModel(value = "菜品类别导出实体类")
public class DictDishTypeExportDTO implements Serializable {

    @Excel(name = "菜类")
    @ApiModelProperty(value = "菜品类别名称")
    private String dishTypeName;

    @Excel(name = "备注")
    @ApiModelProperty(value = "备注")
    private String remark;

}
