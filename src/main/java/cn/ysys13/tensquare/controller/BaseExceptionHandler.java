package cn.ysys13.tensquare.controller;


import cn.ysys13.tensquare.common.entity.Result;
import cn.ysys13.tensquare.common.entity.StatusCode;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * 统一异常处理类
 */
@RestControllerAdvice
public class BaseExceptionHandler {
	
    @ExceptionHandler(value = Exception.class)
    @ResponseBody
    public Result error(Exception e) {
        e.printStackTrace();
        String message = e.getMessage();
        //e.getMessage()用户看不懂
        //根据具体的异常 返回相应的用户能看懂的错误提示信息
        if(e instanceof NullPointerException){
            message = "数据为空";
        }
        else if(e instanceof EmptyResultDataAccessException){
            message = "需要操作的数据不存在";
        }
        return new Result(false, StatusCode.ERROR, message);
    }
}