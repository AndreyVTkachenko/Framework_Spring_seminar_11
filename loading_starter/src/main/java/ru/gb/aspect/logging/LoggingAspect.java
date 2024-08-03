package ru.gb.aspect.logging;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;

@Slf4j
@Aspect
@RequiredArgsConstructor
public class LoggingAspect {

    private final LoggingProperties properties;

    @Pointcut("@annotation(ru.gb.aspect.starter.Logging)")
    public void serviceMethodsPointcut() {
    }

    @Before(value = "serviceMethodsPointcut()")
    public void beforeServiceMethod(JoinPoint jp) {
        String methodName = jp.getSignature().getName();
        Object[] args = jp.getArgs();
        StringBuilder argsInfo = new StringBuilder();

        for (Object arg : args) {
            if (argsInfo.length() > 0) {
                argsInfo.append(", ");
            }
            argsInfo.append(arg.getClass().getSimpleName()).append(" = ").append(arg);
        }

        log.atLevel(properties.getLevel()).log("Before -> {}#{}({})", jp.getTarget().getClass().getSimpleName(), methodName, argsInfo);
    }
}
