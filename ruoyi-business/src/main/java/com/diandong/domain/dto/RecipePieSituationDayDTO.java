package com.diandong.domain.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @Classname RecipePieSituationDayDTO
 * @Description 派菜日期集合和数量实体
 * @Date 2022/5/16 16:02
 * @Created by YuLiu
 */
@Data
@ApiModel(value = "派菜日期集合和数量实体")
public class RecipePieSituationDayDTO implements Serializable {

    private static final long serialVersionUID = 154521519114815412L;

    /**
     * 派菜日期集合
     */
    @ApiModelProperty(value = "派菜日期集合")
    private List<String> list;

    /**
     * 当前时间段个数
     */
    @ApiModelProperty(value = "当前时间段内个数")
    private Integer count;

}
