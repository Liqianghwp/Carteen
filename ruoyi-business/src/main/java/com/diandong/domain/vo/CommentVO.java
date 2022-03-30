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
public class CommentVO implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 编号
     */
    @NotNull(groups = {Update.class})
    @ApiModelProperty(value = "编号")
    private Integer id;

    /**
     * 订单编号
     */
    @ApiModelProperty(value = "订单编号")
    private Long orderId;

    /**
     * 评价内容
     */
    @ApiModelProperty(value = "评价内容")
    private String commentContent;

    /**
     * 评价图片 图片路径
     */
    @ApiModelProperty(value = "评价图片 图片路径")
    private String commentPicture;

    /**
     * 喜好菜品 这个地方存储的应该是菜品id的集合
     */
    @ApiModelProperty(value = "喜好菜品 这个地方存储的应该是菜品id的集合")
    private String deliciousDishes;

    /**
     * 不喜好菜品 这个地方存储的应该是菜品id的集合
     */
    @ApiModelProperty(value = "不喜好菜品 这个地方存储的应该是菜品id的集合")
    private String undeliciousDishes;

    /**
     * 处理描述
     */
    @ApiModelProperty(value = "处理描述")
    private String processDescription;

    /**
     * 处理时间
     */
    @ApiModelProperty(value = "处理时间")
    private LocalDateTime processTime;

    /**
     * 数据状态
     */
    @ApiModelProperty(value = "数据状态")
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