package com.cherkasov.annotation;

import java.lang.reflect.Method;

/**
 * Created by cherkasov_as on 30.06.2017.
 */
public class Processor {

  Object object;
  Method method;
  int priority;

  public Processor(Object object, Method method, int priority) {
    this.object = object;
    this.method = method;
    this.priority = priority;
  }

  public static Object callMethod(Method method, Object object, Object[] args) throws Exception {
    return method.invoke(object, args);
  }


  public static void main(String[] args) throws Exception {
    String testAnno = "#source#";

/*    List<Class<?>> packageClasses =
        Arrays.asList(Main.class, WikiMarkup.class);*/

//    List<Processor> registrations = new ArrayList<>();

    Class<?> ac = WikiMarkup.class;

    Method[] methods = ac.getDeclaredMethods();

    for (Method method : methods) {
      System.out.println(method.getName());

      if (method.isAnnotationPresent(Template.class)) {
        System.out.println("Anno Template");

        Template anno = method.getAnnotation(Template.class);

        if (testAnno.equals(anno.value())){
          System.out.println("equal");
          System.out.println((String) callMethod(method, ac.newInstance(), new Object[] {"test"}));

//          System.out.println(method.invoke(ac.newInstance()));
        }
      }
    }

/*
    for (Class<?> clazz : packageClasses) {
      Processor runAtStartup = clazz.getAnnotation(Template.class);
      if (runAtStartup == null) {
        continue;
      }

      Object instance = clazz.newInstance();
      Method method = clazz.getMethod(runAtStartup.method());

      registrations.add(new Processor(
          instance, method, runAtStartup.priority()));
    }*/

/*    Collections.sort(
        registrations,
        Comparator.<Processor>comparingInt(x -> x.priority)
            .reversed());*/
/*
    for (Processor registration : registrations) {
      registration.callMethod();
    }*/
  }
}