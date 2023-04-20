package com.laolu.shipbackend.common;

import com.laolu.shipbackend.utils.CommonResponse;
import org.springframework.validation.BindException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletRequest;
import javax.validation.ConstraintViolationException;

/**
 * @author wanyi-lu
 * @date Created in 2022/8/18 14:29
 */
@RestControllerAdvice
public class GlobleExceptionHandle {

    @ExceptionHandler(value = BindException.class)
    public CommonResponse<String> exceptionHandler500(MethodArgumentNotValidException e){
        e.printStackTrace();
        return CommonResponse.error("参数验证失败！");
    }

    @ExceptionHandler(value = ConstraintViolationException.class)
    public void exceptionHandler500(ConstraintViolationException e){
        e.printStackTrace();
    }

    @ExceptionHandler(RuntimeException.class)
    public void numberException(RuntimeException e){
        e.printStackTrace();
    }


    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public void numberException(HttpRequestMethodNotSupportedException e, HttpServletRequest request){
        e.printStackTrace();
    }
}
