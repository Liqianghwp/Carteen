package com.ruoyi.framework.web.exception;

import com.ruoyi.common.constant.HttpStatus;
import com.ruoyi.common.constant.MessageConstants;
import com.ruoyi.common.core.domain.BaseResult;
import com.ruoyi.common.exception.DemoModeException;
import com.ruoyi.common.exception.ServiceException;
import com.ruoyi.common.utils.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import javax.servlet.http.HttpServletRequest;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.sql.SQLException;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * 全局异常处理器
 *
 * @author ruoyi
 */
@RestControllerAdvice
public class GlobalExceptionHandler {
    private static final Logger log = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    /**
     * 权限校验异常
     */
    @ExceptionHandler(AccessDeniedException.class)
    public BaseResult handleAccessDeniedException(AccessDeniedException e, HttpServletRequest request) {
        String requestURI = request.getRequestURI();
        log.error("请求地址'{}',权限校验失败'{}'", requestURI, e.getMessage());
        return BaseResult.error(HttpStatus.FORBIDDEN, "没有权限，请联系管理员授权");
    }

    /**
     * 请求方式不支持
     */
    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public BaseResult handleHttpRequestMethodNotSupported(HttpRequestMethodNotSupportedException e,
                                                          HttpServletRequest request) {
        String requestURI = request.getRequestURI();
        log.error("请求地址'{}',不支持'{}'请求", requestURI, e.getMethod());
        return BaseResult.error(e.getMessage());
    }

    /**
     * 自定义验证异常
     */
    @ExceptionHandler(BindException.class)
    public BaseResult handleBindException(BindException e) {
        log.error(e.getMessage(), e);
        List<FieldError> fieldErrors = e.getBindingResult().getFieldErrors();
        List<String> collect = fieldErrors.stream()
                .map(o -> o.getField() + o.getDefaultMessage())
                .collect(Collectors.toList());
        String msg = collect.toString();
        return BaseResult.error(HttpStatus.ILLEGAL_PARAM, msg);
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public BaseResult handleConstraintViolationException(ConstraintViolationException e) {
        log.error(e.getMessage(), e);
        Set<ConstraintViolation<?>> constraintViolations = e.getConstraintViolations();
        List<String> collect = constraintViolations.stream()
                .map(o -> {
                    String fieldName = o.getPropertyPath().toString().split("\\.")[1];
                    return fieldName + o.getMessage();
                })
                .collect(Collectors.toList());
        String msg = collect.toString();
        return BaseResult.error(HttpStatus.ILLEGAL_PARAM, msg);
    }

    /**
     * 自定义验证异常
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public BaseResult handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        log.error(e.getMessage(), e);
        List<FieldError> fieldErrors = e.getBindingResult().getFieldErrors();
        List<String> collect = fieldErrors.stream()
                .map(o -> o.getField() + o.getDefaultMessage())
                .collect(Collectors.toList());
        String msg = collect.toString();
        return BaseResult.error(HttpStatus.ILLEGAL_PARAM, msg);
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public BaseResult handleMethodArgumentTypeMismatchException(MethodArgumentTypeMismatchException e) {
        log.error(e.getMessage(), e);
        MethodArgumentTypeMismatchException methodArgumentTypeMismatchException = (MethodArgumentTypeMismatchException) e;
        String msg = methodArgumentTypeMismatchException.getName() + " type error";
        return BaseResult.error(HttpStatus.ILLEGAL_PARAM, msg);
    }

    /**
     * 演示模式异常
     */
    @ExceptionHandler(DemoModeException.class)
    public BaseResult handleDemoModeException(DemoModeException e) {
        return BaseResult.error("演示模式，不允许操作");
    }

    /**
     * 业务异常
     */
    @ExceptionHandler(ServiceException.class)
    public BaseResult handleServiceException(ServiceException e, HttpServletRequest request) {
        log.error(e.getMessage(), e);
        Integer code = e.getCode();
        return StringUtils.isNotNull(code) ? BaseResult.error(code, e.getMessage()) : BaseResult.error(e.getMessage());
    }

    /**
     * 系统异常
     */
    @ExceptionHandler(Exception.class)
    public BaseResult handleException(Exception e, HttpServletRequest request) {
        String requestURI = request.getRequestURI();
        log.error("请求地址'{}',发生系统异常.", requestURI, e);
        BaseResult baseResult;
        if (e instanceof NoSuchElementException) {
            log.error("Unexpected null ex:", e.getMessage());
            baseResult = BaseResult.error(HttpStatus.DATA_NULL, MessageConstants.DATA_NULL);
        } else if (e instanceof MissingServletRequestParameterException) {
            log.error("request params null error", e.getMessage());
            baseResult = BaseResult.error(HttpStatus.ILLEGAL_PARAM,
                    MessageConstants.ILLEGAL_PARAM + "request params null error");
        } else if (e instanceof HttpMessageNotReadableException) {
            log.error("request body parse error", e.getMessage());
            baseResult = BaseResult.error(HttpStatus.ILLEGAL_PARAM,
                    MessageConstants.ILLEGAL_PARAM + "request body parse error");
        } else if (e instanceof HttpMediaTypeNotSupportedException) {
            log.error("request body type error", e.getMessage());
            baseResult = BaseResult.error(HttpStatus.ILLEGAL_PARAM,
                    MessageConstants.ILLEGAL_PARAM + "request body type error");
        } else if (e instanceof DataIntegrityViolationException) {
            log.error("request params data error", e.getMessage());
            baseResult = BaseResult.error(HttpStatus.DATA_ERROR,
                    MessageConstants.DATA_ERROR + "request params data error");
        } else if (e instanceof SQLException) {
            baseResult = BaseResult.error(HttpStatus.ERROR, "db sql error");
        } else {
            baseResult = BaseResult.error(HttpStatus.ERROR, e.getMessage());
        }

        return baseResult;
    }
}
