package com.diandong.configuration;

import javax.validation.groups.Default;

/**
 * 用于分组校验
 * 继承Default,则全部校验,否则只校验部分属于分组的参数
 * 使用方法@NotBlank(message = "不能为空", groups = {Update.class})
 *
 * @author lingzhi
 * @Validated({Update.class})
 * @date 2021/9/10
 */
public interface Update extends Default {
}
