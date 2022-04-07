package com.diandong.domain.vo;

import com.diandong.configuration.Insert;
import com.diandong.configuration.Update;
import com.diandong.domain.po.RecipeDetailPO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.io.Serializable;
import java.util.List;

/**
 * VO实体类
 *
 * @author YuLiu
 * @date 2022-04-02
 */
@Data
@ApiModel("食谱VO实体类")
public class RecipeVO implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 编号
     */
    @NotNull(groups = {Update.class})
    @ApiModelProperty(value = "编号")
    private Long id;

    /**
     * 食谱名称
     */
    @ApiModelProperty(value = "食谱名称")
    private String recipeName;

    /**
     * 食谱日期
     */
    @ApiModelProperty(value = "食谱日期")
    private LocalDate recipeDate;

    /**
     * 添加方式id
     */
    @ApiModelProperty(value = "添加方式id")
    private Long addWayId;

    /**
     * 添加方式名称
     */
    @ApiModelProperty(value = "添加方式名称")
    private String addWayName;

    /**
     * 状态
     */
    @ApiModelProperty(value = "状态")
    private Integer status;

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
     * 食谱菜品详情
     */
    @ApiModelProperty(value = "食谱菜品详情")
    private List<RecipeDetailVO> recipeDetailList;
    /**
     * 删除的菜品详情
     */
    @ApiModelProperty(value = "要删除的菜品详情ID集合")
    private List<Long> delRecipeDetailIdList;

}