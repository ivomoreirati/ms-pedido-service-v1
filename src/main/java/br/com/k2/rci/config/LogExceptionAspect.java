package br.com.k2.rci.config;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class LogExceptionAspect {

    private static final String MENSAGEM_EXCECAO = "Exceção lançada no método {}: {}";

    private Exception exception;

    @AfterThrowing(pointcut = "execution(* br.com.tlf.pco3..*(..))", throwing = "ex")
    public void logException(JoinPoint joinPoint, Throwable ex) {

        var nomeMetodo = joinPoint.getSignature().getName();
        var nomeClasse = joinPoint.getSignature().getDeclaringTypeName();

        if ((exception == null || exception != ex) && ex instanceof Exception exTyped) {
            exception = exTyped;
            Logger log = LoggerFactory.getLogger(nomeClasse);
            log.error(MENSAGEM_EXCECAO, nomeMetodo, ex.getMessage(), ex);
        }

    }

}