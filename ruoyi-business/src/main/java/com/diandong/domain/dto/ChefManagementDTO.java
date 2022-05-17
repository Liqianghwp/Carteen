package com.diandong.domain.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import com.ruoyi.common.annotation.Excel;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 厨师管理DTO实体类
 *
 * @author YuLiu
 * @date 2022-05-16
 */
@Data
@ApiModel("厨师管理DTO实体类")
public class ChefManagementDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 编号
     */
    @ApiModelProperty(value = "编号")
    private Long id;

    /**
     * 厨师姓名
     */
    @Excel(name = "厨师姓名", sort = 1)
    @ApiModelProperty(value = "厨师姓名")
    private String chefName;

    /**
     * 性别
     */
    @ApiModelProperty(value = "性别")
    private Long sex;

    /**
     * 联系电话
     */
    @Excel(name = "联系电话",sort = 3)
    @ApiModelProperty(value = "联系电话")
    private String phone;

    /**
     * 职务
     */
    @Excel(name = "职务",sort = 4)
    @ApiModelProperty(value = "职务")
    private String jobTitle;

    /**
     * 家庭住址
     */
    @ApiModelProperty(value = "家庭住址")
    private String homeAddress;

    /**
     * 厨师图片
     */
    @ApiModelProperty(value = "厨师图片")
    private String chefPic;

    /**
     * 厨师详情
     */
    @ApiModelProperty(value = "厨师详情")
    private String chefDetails;

    /**
     * 创建者
     */
    @ApiModelProperty(value = "创建者")
    private Long createBy;

    /**
     * 创建时间 默认为当前时间
     */
    @Excel(name = "创建日期",sort = 6,dateFormat = "yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(value = "创建时间 默认为当前时间")
    private LocalDateTime createTime;

    /**
     * 更新者
     */
    @ApiModelProperty(value = "更新者")
    private Long updateBy;

    /**
     * 更新时间
     */
    @ApiModelProperty(value = "更新时间")
    private LocalDateTime updateTime;


    @Excel(name = "性别",sort = 2)
    @ApiModelProperty(value = "性别名称")
    private String sexName;

    /**
     * 创建人姓名
     */
    @Excel(name = "创建人",sort = 5)
    @ApiModelProperty(value = "创建人姓名")
    private String createName;

}