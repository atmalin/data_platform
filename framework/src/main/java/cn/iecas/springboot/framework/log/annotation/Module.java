package cn.iecas.springboot.framework.log.annotation;

import org.springframework.core.annotation.AliasFor;

import java.lang.annotation.*;

/**
 * 模块名称注解
 *
 * @author ch
 * @date 2021-09-29
 **/
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Module {

    /**
     * 模块名称
     *
     * @return
     */
    String name() default "";

    /**
     * 模块名称
     *
     * @return
     */
    @AliasFor("name")
    String value() default "";

}
