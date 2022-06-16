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
 * 菜品评价记录VO实体类
 *
 * @author YuLiu
 * @date 2022-06-16
 */
@Data
@ApiModel("菜品评价记录VO实体类")
public class DishesEvaluationsRecordsVO extends BaseEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 编号
     */
    @NotNull(groups = {Update.class})
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
     * 菜品id
     */
    @ApiModelProperty(value = "菜品id")
    private Long dishesId;

    /**
     * 菜品名称
     */
    @ApiModelProperty(value = "菜品名称")
    private String dishesName;

    /**
     * 好评数量
     */
    @ApiModelProperty(value = "好评数量")
    private Integer goodNum;

    /**
     * 差评数量
     */
    @ApiModelProperty(value = "差评数量")
    private Integer badNum;

    /**
     * 好评度
     */
    @ApiModelProperty(value = "好评度")
    private Double praise;


}