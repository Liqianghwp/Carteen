package com.diandong.domain.vo;

import com.diandong.configuration.Insert;
import com.diandong.configuration.PhoneConstraint;
import com.diandong.configuration.Update;
import com.diandong.domain.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.List;

/**
 * 厨师管理VO实体类
 *
 * @author YuLiu
 * @date 2022-05-16
 */
@Data
@ApiModel("厨师管理VO实体类")
public class ChefManagementVO extends BaseEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 编号
     */
    @NotNull(groups = {Update.class})
    @ApiModelProperty(value = "编号")
    private Long id;

    /**
     * 厨师姓名
     */
    @NotBlank(groups = {Insert.class},message = "厨师姓名不能为空")
    @ApiModelProperty(value = "厨师姓名")
    private String chefName;

    /**
     * 性别
     */
    @NotNull(groups = {Insert.class},message = "性别不能为空")
    @ApiModelProperty(value = "性别")
    private Long sex;

    /**
     * 联系电话
     */
    @ApiModelProperty(value = "联系电话")
    private String phone;

    /**
     * 职务
     */
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
    @NotBlank(groups = {Insert.class},message = "厨师图片不能为空")
    @ApiModelProperty(value = "厨师图片")
    private String chefPic;

    /**
     * 厨师详情
     */
    @ApiModelProperty(value = "厨师详情")
    private String chefDetails;

    /**
     * 勾选id集合
     */
    @ApiModelProperty(value = "勾选id集合")
    private List<Long> ids;


}