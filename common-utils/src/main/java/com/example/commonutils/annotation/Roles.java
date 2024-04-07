package com.example.commonutils.annotation;

import org.springframework.core.annotation.AliasFor;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface Roles {
    enum Role {
        EDITOR, USER
    }
    @AliasFor("roles")
    String[] value() default {};

    @AliasFor("value")
    String[] roles() default {};
}
