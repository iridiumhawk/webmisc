package com.cherkasov.annotation;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;

/**
 * Created by cherkasov_as on 30.06.2017.
 */
public class Processor {

    private Object obj;
    private Class<? extends Annotation> annoClass = Template.class;
//    private Class<Template> annoClass1 = Template.class;

    public Processor(Object object) { //, Class<? extends Annotation> clazz
        this.obj = object;
//        this.annoClass =  clazz;
    }
/*
    private Object callMethod(Method method, Object object, String... args) throws Exception {
        return method.invoke(object, args);
    }*/


    public static void main(String[] args) throws Exception {
        Processor proc = new Processor(new WikiMarkup()); // del Template, Template.class
//        System.out.println(proc.runByAnnotation("source"));
    }

    public Object runByAnnotation(String testAnno, String... args) {
//    public <T extends Annotation> Object runByAnnotation(String testAnno, Class<T> ann, String... args) {
        Class<?> ac = obj.getClass();//WikiMarkup.class;
        Method[] methods = ac.getDeclaredMethods();

        for (Method method : methods) {
//            System.out.println(method.getName());
            Annotation[] methodAnnotations = method.getDeclaredAnnotations();
//            T annotation = method.getDeclaredAnnotation(ann);

            if (method.isAnnotationPresent(annoClass)) {
//                System.out.println("Anno Template");

//                for (Annotation methodAnnotation : methodAnnotations) {
//                    if (methodAnnotation instanceof Template){
//                        if (testAnno.equals(((Template) methodAnnotation).value())) {
                if (testAnno.equals(((Template) method.getAnnotation(annoClass)).value())) {
                    System.out.println("equal");
                    try {

                        if (method.getParameterCount() == 0) {
                            System.out.println("Args null - " + method.getName());
                            return method.invoke(obj);
                        } else {
                            System.out.println("Args not null - " + method.getName() + " args - " + args.length);
                            return method.invoke(obj, (Object) args);
                        }

//                        return callMethod(method, obj, args);
                    } catch (Exception e) {
                        System.out.println(e.getMessage());
                        e.printStackTrace();
                    }

                }
            }
        }
//            }
//        }
        return "";
    }
}