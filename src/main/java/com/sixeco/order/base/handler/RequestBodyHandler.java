package com.sixeco.order.base.handler;

import com.sixeco.order.base.context.DecryptHttpInputMessage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.core.MethodParameter;
import org.springframework.http.HttpInputMessage;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.RequestBodyAdviceAdapter;

import java.lang.reflect.Type;

/**
 * 请求体数据处理类
 *
 * @author: Zhanghe
 * @date: 2019-04-04
 */
@ControllerAdvice
@ConditionalOnProperty(prefix = "spring.crypto.request.decrypt", name = "enabled" , havingValue = "true", matchIfMissing = true)
@Slf4j
public class RequestBodyHandler extends RequestBodyAdviceAdapter {

    @Value("${spring.crypto.request.decrypt.charset}")
    private String charset;

    @Value("${spring.crypto.request.decrypt.primary-key}")
    private String privateKey;

    @Override
    public boolean supports(MethodParameter methodParameter, Type type, Class<? extends HttpMessageConverter<?>> aClass) {
        return true;
    }

    @Override
    public HttpInputMessage beforeBodyRead(HttpInputMessage inputMessage, MethodParameter parameter, Type targetType, Class<? extends HttpMessageConverter<?>> converterType) {
        if(ScanAnnotationHandler.needDecrypt(parameter)) {
            return new DecryptHttpInputMessage(inputMessage, charset, privateKey);
        }
        return inputMessage;
    }
}
