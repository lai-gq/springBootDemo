package com.lai.demo.springAop;

import java.util.Arrays;

import javax.servlet.http.HttpServletRequest;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

/**
 * springbootAOP切面类配置
 * 类注解:
 * 		@Aspect 将一个类定义为一个切面类
 *  	@order(i) 标记切面类的处理优先级,i值越小,优先级别越高.PS:可以注解类,也能注解到方法上
 *  方法注解：
 *  	@Pointcut 定义一个方法为切点里面的内容为一个表达式,下面详细介绍
 *      @Before 在切点前执行方法,内容为指定的切点
 *      @After 在切点后,return前执行,
 *      @AfterReturning 在切入点,return后执行,如果想对某些方法的返回参数进行处理,可以在这操作
 *      @Around 环绕切点,在进入切点前,跟切点后执行
 *      @AfterThrowing 在切点后抛出异常进行处理
 *      @order(i) 标记切点的优先级,i越小,优先级越高
 * @author Administrator
 */
@Aspect //创建一个Aspect切面类
@Component //有springIOC扫描管理
public class aopAspect {
 
    private Logger logger = LoggerFactory.getLogger(aopAspect.class);
 
    /**
     * 定义切入点(执行的条件的表达式，无返回值)
     * 		execution:用于匹配子表达式。如下匹配com.lai.demo.commons.controller包下.所有的类.所有的方法(任意参数)
     * 		within:用于匹配连接点所在的Java类或者包。
     * 		this:用于向通知方法中传入代理对象的引用。
     * 		target：用于向通知方法中传入目标对象的引用。
     * 		args：用于将参数传入到通知方法中。
     * 具体参考:https://www.cnblogs.com/xiaosiyuan/p/6484005.html
     */
    @Pointcut("execution(public * com.lai.demo.commons.controller.*.*(..))")
    public void webLog(){}
 
    /**
     * 通知类型:前置通知(方法调用前被调用)
     * @param joinPoint
     * @throws Throwable
     */
    @Before("webLog()")
    public void doBefore(JoinPoint joinPoint) throws Throwable {
    	 logger.info("aop拦截,正在做前置处理...." );
        // 接收到请求，记录请求内容
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();
        // 记录下请求内容
        logger.info("	请求地址:" + request.getRequestURL().toString());
        logger.info("	请求方式: " + request.getMethod());
        logger.info("	请求IP: " + request.getRemoteAddr());
        logger.info("	请求类方法: " + joinPoint.getSignature().getDeclaringTypeName() + "." + joinPoint.getSignature().getName());
        logger.info("	请求类方法参数: " + Arrays.toString(joinPoint.getArgs()));
 
    }
 
    /**
     * 后置最终通知（目标方法只要执行完了就会执行后置通知方法）
     * @param joinPoint
     */
    @After("webLog()")
    public void doAfterAdvice(JoinPoint joinPoint){
    	logger.info("aop拦截,后置通知执行了(return前)");
    }
    
    /**
     * 通知类型有:后置最终通知
     * 		如果参数中的第一个参数为JoinPoint，则第二个参数为返回值的信息
     *      如果参数中的第一个参数不为JoinPoint，则第一个参数为returning中对应的参数
     *      限定了只有目标方法返回值与通知方法相应参数类型时才能执行后置返回通知，
     *      否则不执行，对于returning对应的通知方法参数为Object类型将匹配任何目标返回值
     * @param ret
     * @throws Throwable
     */
    @AfterReturning(returning = "ret", pointcut = "webLog()")
    public void doAfterReturning(Object ret) throws Throwable {
    	 logger.info("aop拦截,正在做后置处理...." );
    	 // 处理完请求，返回内容
    	 logger.info("	返回结果 : " + ret);
    }
    
    /**
     * 后置异常通知
     * 		定义一个名字，该名字用于匹配通知实现方法的一个参数名，当目标方法抛出异常返回后，将把目标方法抛出的异常传给通知方法；
     *  	throwing 限定了只有目标方法抛出的异常与通知方法相应参数异常类型时才能执行后置异常通知，否则不执行，
     *      对于throwing对应的通知方法参数为Throwable类型将匹配任何异常。
     * @param joinPoint
     * @param exception
     */
    @AfterThrowing(value = "webLog()",throwing = "exception")
    public void doAfterThrowingAdvice(JoinPoint joinPoint,Throwable exception){
    	logger.info("	aop拦截,后置异常通知...." );
        //目标方法名：
    	logger.info("	目标方法名 : " + joinPoint.getSignature().getName());
    }
 
    /**
     * 环绕通知：
     *   环绕通知非常强大，可以决定目标方法是否执行，什么时候执行，执行时是否需要替换方法参数，执行完毕是否需要替换返回值。
     *   环绕通知第一个参数必须是org.aspectj.lang.ProceedingJoinPoint类型
     */
    @Around("webLog()")
    public Object doAroundAdvice(ProceedingJoinPoint proceedingJoinPoint){
    	logger.info("	aop拦截,环绕通知的目标方法名:"+proceedingJoinPoint.getSignature().getName());
        try {
            Object obj = proceedingJoinPoint.proceed();
            return obj;
        } catch (Throwable throwable) {
            throwable.printStackTrace();
        }
        return null;
    }
}
