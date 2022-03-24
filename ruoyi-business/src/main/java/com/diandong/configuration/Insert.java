package com.diandong.configuration;

import javax.validation.groups.Default;

/**
 * 用于分组校验
 * 使用方法@NotBlank(message = "不能为空", groups = {Insert.class})
 *
 * @author lingzhi
 * @Validated({Insert.class})
 * @date 2021/9/10
 */
public interface Insert extends Default {
}
