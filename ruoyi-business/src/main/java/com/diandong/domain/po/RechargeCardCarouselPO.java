package com.diandong.domain.po;

import com.baomidou.mybatisplus.annotation.*;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 充值卡触摸屏轮播图PO实体类
 *
 * @author YuLiu
 * @date 2022-05-11
 */
@TableName("wis_recharge_card_carousel")
@Data
@ApiModel("充值卡触摸屏轮播图PO实体类")
@Accessors(chain = true)
public class RechargeCardCarouselPO implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 编号
     */
    @TableId(value = "id", type = IdType.AUTO)
    @ApiModelProperty(value = "编号")
    private Long id;

    /**
     * 轮播图片
     */
    @TableField(value = "carousel_pic")
    @ApiModelProperty(value = "轮播图片")
    private String carouselPic;

    /**
     * 轮播图名称
     */
    @TableField(value = "carousel_name")
    @ApiModelProperty(value = "轮播图名称")
    private String carouselName;

    /**
     * 创建者
     */
    @TableField(value = "create_by", fill = FieldFill.INSERT)
    @ApiModelProperty(value = "创建者")
    private Long createBy;

    /**
     * 创建时间 默认为当前时间
     */
    @TableField(value = "create_time", fill = FieldFill.INSERT)
    @ApiModelProperty(value = "创建时间 默认为当前时间")
    private LocalDateTime createTime;

    /**
     * 更新者
     */
    @TableField(value = "update_by", fill = FieldFill.INSERT_UPDATE)
    @ApiModelProperty(value = "更新者")
    private Long updateBy;

    /**
     * 更新时间
     */
    @TableField(value = "update_time", fill = FieldFill.INSERT_UPDATE)
    @ApiModelProperty(value = "更新时间")
    private LocalDateTime updateTime;

    @TableField(exist = false)
    @ApiModelProperty(value = "显示顺序")
    private Integer sort;

}