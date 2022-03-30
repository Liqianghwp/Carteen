package com.diandong.domain.vo;

import com.diandong.configuration.Insert;
import com.diandong.configuration.Update;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
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
public class


DishesTypeVO implements Serializable {
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
     * 分类名称
     */
    @NotEmpty(groups = {Insert.class}, message = "分类名称不能为空")
    @ApiModelProperty(value = "分类名称")
    private String typeName;

    /**
     * 排序
     */
    @ApiModelProperty(value = "排序")
    private Integer sort;

    /**
     * 是否显示 （0:不显示，1:显示）
     */
    @ApiModelProperty(value = "是否显示 （0:不显示，1:显示）")
    private Integer isShow;

    /**
     * 类型标签
     */
    @ApiModelProperty(value = "类型标签")
    private String typeLabel;

    /**
     * 是否包装
     */
    @ApiModelProperty(value = "是否包装")
    private Integer isPackage;

    /**
     * 当前类型的uuid （当前未知使用方式字段）
     */
    @ApiModelProperty(value = "当前类型的uuid （当前未知使用方式字段）")
    private String uuid;

    /**
     * 父级uuid (当前未知使用方式字段)
     */
    @ApiModelProperty(value = "父级uuid (当前未知使用方式字段)")
    private String puuid;

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