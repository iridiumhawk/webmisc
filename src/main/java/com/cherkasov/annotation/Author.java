package com.cherkasov.annotation;

public @interface Author {
    //напиши свой код
    String value() default "";

    Position position() default Position.OTHER;
}
