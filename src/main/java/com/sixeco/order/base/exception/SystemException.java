package com.sixeco.order.base.exception;

import lombok.Getter;

/**
 * 异常封装
 *
 * @author: Zhanghe
 * @date: 2019-03-28
 */
public class SystemException extends RuntimeException {

    @Getter
    private Integer code;

    public SystemException(ServiceException serviceException) {
        super(serviceException.getMessage());
        this.code = serviceException.getCode();
    }

    public SystemException(Integer code, String message) {
        super(message);
        this.code = code;
    }
}
