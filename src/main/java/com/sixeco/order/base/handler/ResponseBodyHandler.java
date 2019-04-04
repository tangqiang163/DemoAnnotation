package com.sixeco.order.base.handler;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sixeco.order.base.context.RtnInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

/**
 * 响应体数据处理类
 *
 * @author: Zhanghe
 * @date: 2019-04-04
 */
@ControllerAdvice
@ConditionalOnProperty(prefix = "spring.crypto.response.encrypt", name = "enabled" , havingValue = "true", matchIfMissing = true)
@Slf4j
public class ResponseBodyHandler implements ResponseBodyAdvice {
    @Override
    public boolean supports(MethodParameter methodParameter, Class aClass) {
        return false;
    }

    @Override
    public Object beforeBodyWrite(Object o, MethodParameter methodParameter, MediaType mediaType, Class aClass, ServerHttpRequest serverHttpRequest, ServerHttpResponse serverHttpResponse) {
        if(!ScanAnnotationHandler.needEncrypt(methodParameter)) {
            return o;
        }
        if (!(o instanceof RtnInfo)) {
            return o;
        }
        RtnInfo rtnInfo = (RtnInfo) o;
        Object data = rtnInfo.getData();
        if(data == null) {
            return o;
        }
        String readyData;
        Class<?> dataClass = data.getClass();
        if(dataClass.isPrimitive() || (data instanceof String)){
            readyData = String.valueOf(data);
        }else {
            ObjectMapper objectMapper = new ObjectMapper();
            try {
                readyData = objectMapper.writeValueAsString(data);
            } catch (JsonProcessingException e) {
                log.error(e.getMessage());
            }
        }
        //rtnInfo.setData(crypto.encrypt(readyData, charset));
        return rtnInfo;
    }
}
