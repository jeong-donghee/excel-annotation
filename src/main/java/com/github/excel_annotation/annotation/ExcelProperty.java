package com.github.excel_annotation.annotation;

import com.github.excel_annotation.converter.DefaultConverter;
import com.github.excel_annotation.converter.ExcelConverter;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface ExcelProperty {
    String name() default "";
    Class<? extends ExcelConverter<?>> convert() default DefaultConverter.class;
}
