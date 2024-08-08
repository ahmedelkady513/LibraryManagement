package cc.maids.librarymanagement.aop;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

@Aspect
@Component
@Slf4j
public class ExceptionAspect {
    @AfterThrowing(pointcut = "execution(* cc.maids.librarymanagement..*.*(..))", throwing = "ex")
    public void logError(Exception ex) {
        log.error("Exception thrown : {}", ex.getMessage());
    }
}
