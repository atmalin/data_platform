package cn.iecas.springboot.framework.common.enums.config;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface SwaggerDisplayEnum {

    String code() default "code";

    String desc() default "desc";
}
