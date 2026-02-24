package com.bookstore.springboot.dto.base;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface Filter {
    /**
     * Entity field name. If empty, uses the DTO field name.
     */
    String property() default "";

    /**
     * Filter operator (default is EQUAL).
     */
    FilterOperator operator() default FilterOperator.EQUAL;
}
