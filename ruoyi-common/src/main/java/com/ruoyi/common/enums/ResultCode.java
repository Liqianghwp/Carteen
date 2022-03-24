package com.ruoyi.common.enums;

import com.ruoyi.common.constant.HttpStatus;

/**
 * @author lingzhi
 * @date 2022/2/24
 */
public enum ResultCode {
    SUCCESS(HttpStatus.SUCCESS, "操作成功"),
    FAILED(HttpStatus.ERROR, "操作失败");
    private Integer code;
    private String msg;

    ResultCode(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
