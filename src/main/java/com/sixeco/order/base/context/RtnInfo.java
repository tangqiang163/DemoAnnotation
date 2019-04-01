package com.sixeco.order.base.context;

import com.sixeco.order.base.constant.RtnConstant;
import com.sixeco.order.base.exception.ServiceException;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 统一返回值对象
 *
 * @author: Zhanghe
 * @date: 2019-03-28
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RtnInfo<T> {

    // 快速成功返回（不带返回数据）
    public static final RtnInfo SUCCESS = RtnInfo.builder().code(RtnConstant.Code.SUCCESS_CODE).msg(RtnConstant.Msg.SUCCESS_MSG).build();

    // 服务器异常返回
    public static final RtnInfo SERVER_ERROR = RtnInfo.builder().code(RtnConstant.Code.SERVER_ERROR_CODE).msg(RtnConstant.Msg.SERVER_ERROR_MSG).build();

    // 成功返回，带数据
    public static RtnInfo success(Object data) {
        return RtnInfo.builder().code(RtnConstant.Code.SUCCESS_CODE).msg(RtnConstant.Msg.SUCCESS_MSG).data(data).build();
    }

    // 各个业务的错误返回
    public static RtnInfo error(Integer code, String msg) {
        return RtnInfo.builder().code(code).msg(msg).build();
    }

    public static RtnInfo error(ServiceException serviceException) {
        return RtnInfo.builder()
                .code(serviceException.getCode())
                .msg(serviceException.getMessage())
                .build();
    }

    //返回信息
    private String msg;
    //返回状态
    private Integer code;
    //返回值
    private T data;
}
