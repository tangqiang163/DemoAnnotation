package com.sixeco.order.base.aop;

import com.sixeco.order.base.constant.RtnConstant;
import com.sixeco.order.base.context.RtnInfo;
import com.sixeco.order.module.redis.service.RedisService;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

/**
 * 防止重复提交切面
 *
 * @author: Zhanghe
 * @date: 2019-04-04
 */
@Aspect
@Component
@Slf4j
public class NoRepeatSubmitAspect extends BaseAspect {

    @Autowired
    private RedisService redisService;

    @Around("@annotation(com.sixeco.order.base.annotation.NoRepeatSubmit)")
    public Object checkRepeatSubmit(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        String key = RequestContextHolder.getRequestAttributes().getSessionId() + "-" + attributes.getRequest().getServletPath();
        // 如果缓存中有这个url视为重复提交
        if (redisService.get(key) == null) {
            redisService.set(key, 0, 2000L);
            return proceedingJoinPoint.proceed();
        }
        log.info("重复提交");
        return RtnInfo.error(RtnConstant.Code.REPEAT_SUBMIT, RtnConstant.Msg.REPEAT_SUBMIT);
    }
}
