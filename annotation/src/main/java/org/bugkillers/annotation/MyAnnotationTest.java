package org.bugkillers.annotation;

import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * 注解使用类
 * Created by liuxinyu on 15/4/15.
 */
public class MyAnnotationTest {

    @MyAnnotation(value = "abc")
    public void execute(){
        System.out.println("method");
    }

    @MyAnnotation(arrays = {"123","456"})
    public void execute2(){
        System.out.println("method");
    }


    public static void main(String[] args) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        MyAnnotationTest test = new MyAnnotationTest();
        //获取MyAnnotationTest的Class实例
        Class<MyAnnotationTest> clazz = MyAnnotationTest.class;
        //获取需要处理的方法Method实例
        Method method = clazz.getMethod("execute2",new Class[]{});
        //判断该方法是否包含MyAnnotation注解
        if (method.isAnnotationPresent(MyAnnotation.class)){
            //获取该方法的MyAnnotation注解实例
            MyAnnotation myAnnotation = method.getAnnotation(MyAnnotation.class);
            //执行该方法
            method.invoke(test,new Object[]{});
            //获取myAnnotation
            String [] value = myAnnotation.arrays();
            System.out.println(value[0]);
        }
        //获取方法上的所有注解
        Annotation[] annotations = method.getDeclaredAnnotations();
        for (Annotation annotation : annotations){
            System.out.println(annotation);
        }
    }
}
