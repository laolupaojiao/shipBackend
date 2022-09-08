package com.laolu.shipbackend.common;

import com.laolu.shipbackend.utils.JsonResponse;
import org.hibernate.validator.internal.engine.path.PathImpl;
import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletRequest;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;

/**
 * @author wanyi-lu
 * @date Created in 2022/8/18 14:29
 */
@RestControllerAdvice
public class GlobleExceptionHandle {

    @ExceptionHandler(value = BindException.class)
    public String exceptionHandler500(BindException e){
        e.printStackTrace();
        StringBuilder stringBuilder = new StringBuilder();
        for (ObjectError error : e.getAllErrors()) {
            stringBuilder.append(((FieldError) error).getField());
            stringBuilder.append(" ");
            stringBuilder.append(error.getDefaultMessage());
        }
        return JsonResponse.message(500,"请求失败！ " + stringBuilder);
    }

    @ExceptionHandler(value = ConstraintViolationException.class)
    public String exceptionHandler500(ConstraintViolationException e){
        e.printStackTrace();
        StringBuilder stringBuilder = new StringBuilder();
        for (ConstraintViolation<?> error : e.getConstraintViolations()) {
            PathImpl pathImpl = (PathImpl) error.getPropertyPath();
            String paramName = pathImpl.getLeafNode().getName();
            stringBuilder.append(paramName);
            stringBuilder.append(" ");
            stringBuilder.append(error.getMessage());
        }
        return JsonResponse.message(500,"请求失败！" + stringBuilder);
    }

    @ExceptionHandler(RuntimeException.class)
    public String numberException(RuntimeException e){
        e.printStackTrace();
        return JsonResponse.message(500, e.getMessage());
    }


    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public String numberException(HttpRequestMethodNotSupportedException e, HttpServletRequest request){
        return JsonResponse.message(500, "无法接受"+request.getMethod()+"请求方法");
    }
}
