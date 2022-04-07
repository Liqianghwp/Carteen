package com.diandong.domain.vo;

import com.diandong.configuration.Insert;
import com.diandong.configuration.Update;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.io.Serializable;
import java.util.List;

/**
 * VO实体类
 *
 * @author YuLiu
 * @date 2022-04-06
 */
@Data
@ApiModel("购物车VO实体类")
public class ShopCartVO implements Serializable {
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
    @NotNull(groups = {Insert.class},message = "食堂id不能为空")
    @ApiModelProperty(value = "食堂id")
    private Long canteenId;

    /**
     * 食堂名称
     */
    @ApiModelProperty(value = "食堂名称")
    private String canteenName;

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

    /**
     * 购物车详情列表
     */
    @Valid
    @NotNull(groups = {Insert.class},message = "购物车详情集合不能为空")
    @ApiModelProperty(value = "购物车详情集合")
    private List<ShopCartDetailVO> shopCartDetailVOList;


}