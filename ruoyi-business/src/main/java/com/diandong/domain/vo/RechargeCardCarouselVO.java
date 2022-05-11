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
 * 充值卡触摸屏轮播图VO实体类
 *
 * @author YuLiu
 * @date 2022-05-11
 */
@Data
@ApiModel("充值卡触摸屏轮播图VO实体类")
public class RechargeCardCarouselVO extends BaseEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 编号
     */
    @NotNull(groups = {Update.class})
    @ApiModelProperty(value = "编号")
    private Long id;

    /**
     * 轮播图片
     */
    @ApiModelProperty(value = "轮播图片")
    private String carouselPic;

    /**
     * 轮播图名称
     */
    @ApiModelProperty(value = "轮播图名称")
    private String carouselName;


}