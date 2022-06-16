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
 * 食堂轮播图VO实体类
 *
 * @author YuLiu
 * @date 2022-06-16
 */
@Data
@ApiModel("食堂轮播图VO实体类")
public class CanteenCarouselVO extends BaseEntity implements Serializable {
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
     * 图片
     */
    @ApiModelProperty(value = "图片")
    private String carouselPic;

    /**
     * 排序
     */
    @ApiModelProperty(value = "排序")
    private Integer sort;


}