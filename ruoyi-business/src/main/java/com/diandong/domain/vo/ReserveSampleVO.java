package com.diandong.domain.vo;

import com.diandong.configuration.Insert;
import com.diandong.configuration.Update;
import com.diandong.domain.BaseEntity;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.io.Serializable;
import java.util.List;

/**
 * 预留样品VO实体类
 *
 * @author YuLiu
 * @date 2022-05-16
 */
@Data
@ApiModel("预留样品VO实体类")
public class ReserveSampleVO extends BaseEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 编号
     */
    @NotNull(groups = {Update.class})
    @ApiModelProperty(value = "编号")
    private Long id;

    /**
     * 留样食堂ID
     */
    @ApiModelProperty(value = "留样食堂ID")
    private Long reserveCanteenId;

    /**
     * 留样日期
     */
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @ApiModelProperty(value = "留样日期")
    private LocalDateTime reserveDate;

    /**
     * 餐次
     */
    @ApiModelProperty(value = "餐次")
    private Long mealTimes;

    /**
     * 食品名称
     */
    @ApiModelProperty(value = "食品名称")
    private String foodName;

    /**
     * 留样数量
     */
    @ApiModelProperty(value = "留样数量")
    private String reserveNum;

    /**
     * 储存地点
     */
    @ApiModelProperty(value = "储存地点")
    private Long storageLocation;

    /**
     * 留样人
     */
    @ApiModelProperty(value = "留样人")
    private String reserveName;

    /**
     * 预警周期
     */
    @ApiModelProperty(value = "预警周期")
    private Integer warningDay;

    /**
     * 备注
     */
    @ApiModelProperty(value = "备注")
    private String remark;

    /**
     * 状态 0：未处理-有效；1：未处理-报警；2：已处理
     */
    @ApiModelProperty(value = "状态 0：未处理-有效；1：未处理-报警；2：已处理")
    private Integer state;

    /**
     * 导出勾选的id集合
     */
    @ApiModelProperty("导出勾选的id集合")
    private List<Long> ids;

}