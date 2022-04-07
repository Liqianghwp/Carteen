package com.diandong.domain.vo;

import com.diandong.configuration.Insert;
import com.diandong.configuration.Update;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.models.auth.In;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.io.Serializable;

/**
 * VO实体类
 *
 * @author YuLiu
 * @date 2022-04-06
 */
@Data
@ApiModel("购物车详情VO实体类")
public class ShopCartDetailVO implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 编号
     */
    @NotNull(groups = {Update.class})
    @ApiModelProperty(value = "编号")
    private Long id;

    /**
     * 购物车id
     */
    @ApiModelProperty(value = "购物车id")
    private Long shopCartId;

    /**
     * 菜品id
     */
    @NotNull(groups = {Insert.class}, message = "菜品ID 不能为空")
    @ApiModelProperty(value = "菜品id")
    private Long dishesId;

    /**
     * 菜品名称
     */
    @ApiModelProperty(value = "菜品名称")
    private String dishesName;

    /**
     * 数量
     */
    @NotNull(groups = {Insert.class}, message = "购买数量不能为空")
    @ApiModelProperty(value = "数量")
    private Integer number;

    /**
     * 数据状态
     */
    @ApiModelProperty(value = "数据状态")
    private Integer dataStatus;

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