package org.bugkillers.annotation;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * 自定义注解
 *
 * @Retention(RetentionPolicy.CLASS) 设置注解的作用域 @see RetentionPolicy
 * <p/>
 * CLASS
 * 编译器将把注释记录在类文件中，但在运行时 VM 不需要保留注释。
 * RUNTIME
 * 编译器将把注释记录在类文件中，在运行时 VM 将保留注释，因此可以反射性地读取。
 * SOURCE
 * 编译器要丢弃的注释。
 * Created by liuxinyu on 15/4/15.
 */
//@Retention(RetentionPolicy.CLASS)
public @interface MyAnnotation {
    //属性 并设置默认值
    String value() default "abc";

    //多变量使用枚举
    MyEnum myEnum() default MyEnum.Rainy;

    //数组属性
    String[] arrays() default "efg";

}

enum MyEnum {
    Sunny,
    Rainy
}