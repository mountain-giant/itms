package com.lister.itms.handler;

import com.lister.itms.enums.ResponseEnum;
import com.lister.itms.exception.BizException;
import com.lister.itms.modal.ResponseResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Describe :
 * Created by Lister on 2018/6/24 下午10:33.
 * Version : 1.0
 */
@Slf4j
@ControllerAdvice
public class GlobalExceptionHandler {

    /**
     * 处理业务异常
     * @param e
     * @return
     */
    @ExceptionHandler(value = BizException.class)
    public @ResponseBody ResponseResult bizExceptionHandler(Exception e) {
        log.info("BIZ FAIL : " + e.getMessage());
        return ResponseResult.createResult(ResponseEnum.FAILED.getCode(),e.getMessage());
    }

    /**
     * 处理其他异常
     * @param e
     * @return
     */
    @ExceptionHandler(value = Exception.class)
    public @ResponseBody ResponseResult exceptionHandler(Exception e) {
        log.error("ERROR LOG : ", e);
        return ResponseResult.createResult(ResponseEnum.ERROR.getCode(),"请求超时，请稍后再试");
    }
    
}
