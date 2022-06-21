package com.ruoyi.pay.model.req;

import lombok.Data;

import java.io.Serializable;

/**
 * @Classname ShopCallBackVO
 * @Description TODO
 * @Date 2022/6/2 20:08
 * @Created by YuLiu
 */

@Data
public class ShopCallBackVO implements Serializable {

    private Integer shopId;

    private Long deptId;

    private Long userId;

}
