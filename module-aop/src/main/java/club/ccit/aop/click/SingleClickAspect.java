package club.ccit.aop.click;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;

import java.lang.reflect.Method;

/**
 * @author swzhang3
 * name: PreventDoubleClickAspect
 * date: 2024/6/19 12:52
 * description:
 **/
@Aspect
public class SingleClickAspect {
    /**
     * 记录上次点击的时间
     */
    private static long lastClickTime;

    /**
     * 定义切点，标记切点为所有被@SingleClick注解的方法
     */
    @Pointcut("execution(@club.ccit.aop.click.SingleClick * *(..))")
    public void SingleClick() {

    }

    /**
     * 定义一个切面方法，包裹切点方法
     */
    @Around("SingleClick()")
    public void aroundJoinPoint(ProceedingJoinPoint joinPoint) throws Throwable {
        // 取出方法的注解
        MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
        Method method = methodSignature.getMethod();
        SingleClick singleClick = method.getAnnotation(SingleClick.class);
        // 判断是否快速点击
        if (singleClick != null) {
            long delayMillis = singleClick.value();
            long currentTime = System.currentTimeMillis();
            if (currentTime - lastClickTime < delayMillis) {
                // 如果点击间隔小于设定的时间，则不执行方法
                return;
            }
            lastClickTime = currentTime;
        }
        joinPoint.proceed();
    }
}