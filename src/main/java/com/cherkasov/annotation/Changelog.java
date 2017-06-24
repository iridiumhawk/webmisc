package com.cherkasov.annotation;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
public @interface Changelog {
    //напиши свой код

    Revision[] value();
}
