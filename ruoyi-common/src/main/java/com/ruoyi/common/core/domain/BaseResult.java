package com.ruoyi.common.core.domain;

import com.baomidou.mybatisplus.extension.api.IErrorCode;
import com.ruoyi.common.enums.ResultCode;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 通用返回结果
 *
 * @author lingzhi
 * @date 2022/2/24
 */
@ApiModel(description = "通用返回结果")
public class BaseResult<T> {
    /**
     * 响应代码
     */
    @ApiModelProperty("响应代码")
    private int code;
    /**
     * 响应信息
     */
    @ApiModelProperty("响应信息")
    private String msg;
    /**
     * 数据体
     */
    @ApiModelProperty("数据体")
    private T data;

    protected BaseResult() {
    }

    protected BaseResult(int code, String msg, T data) {
        this.code = code;
        this.msg = msg;
        this.data = data;
    }

    /**
     * 成功返回结果
     */
    public static <T> BaseResult<T> success() {
        return new BaseResult<T>(ResultCode.SUCCESS.getCode(), ResultCode.SUCCESS.getMsg(), null);
    }

    /**
     * 成功返回结果
     *
     * @param data 获取的数据
     */
    public static <T> BaseResult<T> success(T data) {
        return new BaseResult<T>(ResultCode.SUCCESS.getCode(), ResultCode.SUCCESS.getMsg(), data);
    }

    /**
     * 成功返回结果
     *
     * @param msg 成功信息
     */
    public static <T> BaseResult<T> successMsg(String msg) {
        return new BaseResult<T>(ResultCode.SUCCESS.getCode(), msg, null);
    }

    /**
     * 成功返回结果
     *
     * @param data    获取的数据
     * @param msg 成功返回的信息
     */
    public static <T> BaseResult<T> success(T data, String msg) {
        return new BaseResult<T>(ResultCode.SUCCESS.getCode(), msg, data);
    }

    /**
     * 错误返回结果
     *
     * @param code    错误码
     * @param msg 错误信息
     * @return
     */
    public static <T> BaseResult<T> error(int code, String msg) {
        return new BaseResult<T>(code, msg, null);
    }

    /**
     * 错误返回结果
     *
     * @param errorCode 错误码
     */
    public static <T> BaseResult<T> error(IErrorCode errorCode) {
        return new BaseResult<T>(Integer.parseInt(errorCode.getCode() + ""), errorCode.getMsg(), null);
    }

    /**
     * 错误返回结果
     *
     * @param msg 错误返回信息
     */
    public static <T> BaseResult<T> error(String msg) {
        return new BaseResult<T>(ResultCode.FAILED.getCode(), msg, null);
    }

    /**
     * 错误返回结果
     */
    public static <T> BaseResult<T> error() {
        return error(ResultCode.FAILED.getMsg());
    }

    public long getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
