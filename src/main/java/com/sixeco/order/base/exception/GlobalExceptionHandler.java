package com.sixeco.order.base.exception;

import com.sixeco.order.base.context.RtnInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 全局异常处理
 *
 * @author: Zhanghe
 * @date: 2019-03-28
 */
@ControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    /**
     * 处理业务异常
     * @author: Zhanghe
     * @date: 2019/3/28
     */
    @ExceptionHandler(SystemException.class)
    @ResponseBody
    public RtnInfo handleCustomSystemException(SystemException e) {
        log.info(e.getMessage(), e);
        return RtnInfo.error(e.getCode(), e.getMessage());
    }

    /**
     * 处理不可知异常
     * @author: Zhanghe
     * @date: 2019/3/28
     */
    @ExceptionHandler(Exception.class)
    @ResponseBody
    public RtnInfo handleException(Exception e) {
        log.error(e.getMessage(), e);
        return RtnInfo.SERVER_ERROR;
    }
}
