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
 * 健康证VO实体类
 *
 * @author YuLiu
 * @date 2022-05-11
 */
@Data
@ApiModel("健康证VO实体类")
public class HealthCertificateVO extends BaseEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 编号
     */
    @NotNull(groups = {Update.class})
    @ApiModelProperty(value = "编号")
    private Long id;

    /**
     * 健康证图片
     */
    @ApiModelProperty(value = "健康证图片")
    private String healthCertPic;

    /**
     * 姓名
     */
    @ApiModelProperty(value = "姓名")
    private String name;

    /**
     * 手机号
     */
    @ApiModelProperty(value = "手机号")
    private String phone;

    /**
     * 有效期开始时间
     */
    @ApiModelProperty(value = "有效期开始时间")
    private LocalDateTime validityStartTime;

    /**
     * 有效期结束时间
     */
    @ApiModelProperty(value = "有效期结束时间")
    private LocalDateTime validityEndTime;

    /**
     * 编号
     */
    @ApiModelProperty(value = "编号")
    private String code;

    /**
     * 性别
     */
    @ApiModelProperty(value = "性别")
    private Integer sex;

    /**
     * 出生日期
     */
    @ApiModelProperty(value = "出生日期")
    private LocalDateTime birthday;

    /**
     * 状态
     */
    @ApiModelProperty(value = "状态")
    private Integer state;


}