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
 * @date 2022-03-23
 */
@Data
@ApiModel("VO实体类")
public class CanteenVO implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 
     */
    @NotNull(groups = {Update.class})
    @ApiModelProperty(value = "")
    private Long id;

    /**
     * 食堂名称
     */
    @ApiModelProperty(value = "食堂名称")
    private String canteenName;

    /**
     * 状态（0正常 1停用）
     */
    @ApiModelProperty(value = "状态（0正常 1停用）")
    private String status;

    /**
     * 食堂图片，存储的是路径地址
     */
    @ApiModelProperty(value = "食堂图片，存储的是路径地址")
    private String canteenPicture;

    /**
     * 食堂介绍
     */
    @ApiModelProperty(value = "食堂介绍")
    private String canteenIntroduce;

    /**
     * 开始时间
     */
    @ApiModelProperty(value = "开始时间")
    private LocalDateTime beginTime;

    /**
     * 结束时间
     */
    @ApiModelProperty(value = "结束时间")
    private LocalDateTime endTime;

}