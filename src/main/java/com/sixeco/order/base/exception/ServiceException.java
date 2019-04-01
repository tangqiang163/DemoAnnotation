package com.sixeco.order.base.exception;

/**
 * 自定义服务异常接口
 *
 * @author: Zhanghe
 * @date: 2019-03-28
 */
public interface ServiceException {

    /**
     * 获取异常编码
     */
    Integer getCode();

    /**
     * 获取异常信息
     */
    String getMessage();
}
