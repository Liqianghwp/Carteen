package com.diandong.domain.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

/**
 * @Classname RecipePieDayDTO
 * @Description 派菜信息返回实体
 * @Date 2022/5/23 18:43
 * @Created by YuLiu
 */
@Data
@ApiModel(value = "派菜信息实体")
@NoArgsConstructor
@AllArgsConstructor
public class RecipePieDayDTO implements Serializable {
    private static final long serialVersionUID = 6502744335690935760L;

    /**
     * 当月派菜日期集合
     */
    @ApiModelProperty(value = "当月派菜日期集合")
    private List<String> monthList;
    /**
     * 当月派菜次数
     */
    @ApiModelProperty(value = "当月派菜次数")
    private Integer monthCount;
    /**
     * 当前季度派菜日期集合
     */
    @ApiModelProperty(value = "当前季度派菜日期集合")
    private List<String> quarterList;
    /**
     * 当前季度派菜次数
     */
    @ApiModelProperty(value = "当前季度派菜次数")
    private Integer quarterCount;

}
