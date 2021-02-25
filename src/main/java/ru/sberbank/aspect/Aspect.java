package ru.sberbank.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.stereotype.Component;


@org.aspectj.lang.annotation.Aspect
@Component(ConfigurableBeanFactory.SCOPE_SINGLETON)
public class Aspect {

    private final static Logger LOG = LoggerFactory.getLogger(Aspect.class);

    @Pointcut("execution(* ru.sberbank.service.*.*(..))")
    public void callAtMyServicePublic() {
    }

    @Before("callAtMyServicePublic()")
    public void beforeServiceMethodInvocation(JoinPoint joinPoint) {
        LOG.info("Invocation method " + joinPoint.getSignature());
    }

    @Around("callAtMyServicePublic()")
    public Object aroundServiceMethodExecution(ProceedingJoinPoint jp) throws Throwable {
        long start = System.currentTimeMillis();
        Object res = jp.proceed();
        long end = System.currentTimeMillis();
        LOG.info("Execution of method " + jp.getSignature() + "took " + (end - start) + " ms");
        return res;
    }


    @After("callAtMyServicePublic()")
    public void afterServiceMethodInvocation(JoinPoint jp) {
        LOG.info(jp.getSignature() + "was invoked ");
    }
}
