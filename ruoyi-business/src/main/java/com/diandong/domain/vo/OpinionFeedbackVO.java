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
 * 意见反馈VO实体类
 *
 * @author YuLiu
 * @date 2022-05-11
 */
@Data
@ApiModel("意见反馈VO实体类")
public class OpinionFeedbackVO extends BaseEntity implements Serializable {
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
     * 意见类型id
     */
    @ApiModelProperty(value = "意见类型id")
    private Long opinionId;

    /**
     * 意见类型
     */
    @ApiModelProperty(value = "意见类型")
    private String opinionType;

    /**
     * 意见内容
     */
    @ApiModelProperty(value = "意见内容")
    private String opinionContent;

    /**
     * 图片 图片地址
     */
    @ApiModelProperty(value = "图片 图片地址")
    private String opinionPicture;

    /**
     * 处理信息 可以用text类型
     */
    @ApiModelProperty(value = "处理信息 可以用text类型")
    private String processInformation;

    /**
     * 状态 0：未处理；1：已查看；2：已处理
     */
    @ApiModelProperty(value = "状态 0：未处理；1：已查看；2：已处理")
    private Integer status;

    /**
     * 匿名状态 0:不匿名；1:匿名
     */
    @ApiModelProperty(value = "匿名状态 0:不匿名；1:匿名")
    private Integer anonymous;

    /**
     * 处理人id
     */
    @ApiModelProperty(value = "处理人id")
    private Long handlerId;

    /**
     * 处理时间
     */

    @ApiModelProperty(value = "处理时间")
    private LocalDateTime processTime;

    @ApiModelProperty(value = "集团id")
    private Long groupId;
}