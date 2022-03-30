package com.diandong.domain.vo;

import com.diandong.configuration.Insert;
import com.diandong.configuration.Update;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.io.Serializable;

/**
 * VO实体类
 *
 * @author YuLiu
 * @date 2022-03-29
 */
@Data
@ApiModel("VO实体类")
public class OpinionFeedbackVO implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 编号
     */
    @NotNull(groups = {Update.class})
    @ApiModelProperty(value = "编号")
    private Long id;

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
     * 处理时间
     */
    @ApiModelProperty(value = "处理时间")
    private LocalDateTime processTime;

    /**
     * 数据状态 0：未处理；1：已查看；2：已处理
     */
    @ApiModelProperty(value = "数据状态 0：未处理；1：已查看；2：已处理")
    private Integer dataState;

    /**
     * 乐观锁
     */
    @ApiModelProperty(value = "乐观锁")
    private Integer version;

    /**
     * 创建人姓名
     */
    @ApiModelProperty(value = "创建人姓名")
    private String createName;

    /**
     * 更新人姓名
     */
    @ApiModelProperty(value = "更新人姓名")
    private String updateName;


}