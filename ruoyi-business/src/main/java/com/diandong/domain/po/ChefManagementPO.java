package com.diandong.domain.po;

import com.baomidou.mybatisplus.annotation.*;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 厨师管理PO实体类
 *
 * @author YuLiu
 * @date 2022-05-16
 */
@TableName("wis_chef_management")
@Data
@ApiModel("厨师管理PO实体类")
@Accessors(chain = true)
public class ChefManagementPO implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 编号
     */
    @TableId(value = "id", type = IdType.AUTO)
    @ApiModelProperty(value = "编号")
    private Long id;

    /**
     * 厨师姓名
     */
    @TableField(value = "chef_name")
    @ApiModelProperty(value = "厨师姓名")
    private String chefName;

    /**
     * 性别
     */
    @TableField(value = "sex")
    @ApiModelProperty(value = "性别")
    private Long sex;

    /**
     * 联系电话
     */
    @TableField(value = "phone")
    @ApiModelProperty(value = "联系电话")
    private String phone;

    /**
     * 职务
     */
    @TableField(value = "job_title")
    @ApiModelProperty(value = "职务")
    private String jobTitle;

    /**
     * 家庭住址
     */
    @TableField(value = "home_address")
    @ApiModelProperty(value = "家庭住址")
    private String homeAddress;

    /**
     * 厨师图片
     */
    @TableField(value = "chef_pic")
    @ApiModelProperty(value = "厨师图片")
    private String chefPic;

    /**
     * 厨师详情
     */
    @TableField(value = "chef_details")
    @ApiModelProperty(value = "厨师详情")
    private String chefDetails;

    /**
     * 数据状态
     */
    @TableField(value = "del_flag")
    @ApiModelProperty(value = "数据状态")
    private Integer delFlag;

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

}