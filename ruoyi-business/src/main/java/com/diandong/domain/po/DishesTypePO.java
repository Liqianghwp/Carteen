package com.diandong.domain.po;

import com.baomidou.mybatisplus.annotation.*;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * PO实体类
 *
 * @author YuLiu
 * @date 2022-03-25
 */
@TableName("dishes_type")
@Data
@ApiModel("PO实体类")
@Accessors(chain = true)
public class DishesTypePO implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 主键id
     */
    @TableId(value = "id", type = IdType.AUTO)
    @ApiModelProperty(value = "主键id")
    private Long id;

    /**
     * 食堂id
     */
    @TableField(value = "canteen_id")
    @ApiModelProperty(value = "食堂id")
    private Long canteenId;

    /**
     * 食堂名称
     */
    @TableField(value = "canteen_name")
    @ApiModelProperty(value = "食堂名称")
    private String canteenName;

    /**
     * 分类名称
     */
    @TableField(value = "type_name")
    @ApiModelProperty(value = "分类名称")
    private String typeName;

    /**
     * 排序
     */
    @TableField(value = "sort")
    @ApiModelProperty(value = "排序")
    private Integer sort;

    /**
     * 是否显示
     */
    @TableField(value = "is_show")
    @ApiModelProperty(value = "是否显示")
    private Integer isShow;

    /**
     * 类型标签
     */
    @TableField(value = "type_label")
    @ApiModelProperty(value = "类型标签")
    private String typeLabel;

    /**
     * 是否包装
     */
    @TableField(value = "is_package")
    @ApiModelProperty(value = "是否包装")
    private Integer isPackage;

    /**
     * uuid
     */
    @TableField(value = "uuid")
    @ApiModelProperty(value = "uuid")
    private String uuid;

    /**
     * 父级UUID
     */
    @TableField(value = "puuid")
    @ApiModelProperty(value = "父级UUID")
    private String puuid;

    /**
     * 数据状态
     */
    @TableField(value = "data_state")
    @ApiModelProperty(value = "数据状态")
    private Integer dataState;

    /**
     * 乐观锁
     */
    @TableField(value = "version")
    @ApiModelProperty(value = "乐观锁")
    private Integer version;

    /**
     * 创建人
     */
    @TableField(value = "create_by", fill = FieldFill.INSERT)
    @ApiModelProperty(value = "创建人")
    private Long createBy;

    /**
     * 创建人姓名
     */
    @TableField(value = "create_name")
    @ApiModelProperty(value = "创建人姓名")
    private String createName;

    /**
     * 创建时间
     */
    @TableField(value = "create_time", fill = FieldFill.INSERT)
    @ApiModelProperty(value = "创建时间")
    private LocalDateTime createTime;

    /**
     * 更新人id
     */
    @TableField(value = "update_by", fill = FieldFill.INSERT_UPDATE)
    @ApiModelProperty(value = "更新人id")
    private Long updateBy;

    /**
     * 更新人姓名
     */
    @TableField(value = "update_name")
    @ApiModelProperty(value = "更新人姓名")
    private String updateName;

    /**
     * 更新时间
     */
    @TableField(value = "update_time", fill = FieldFill.INSERT_UPDATE)
    @ApiModelProperty(value = "更新时间")
    private LocalDateTime updateTime;

}