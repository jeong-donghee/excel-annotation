package com.github.excel_annotation.converter;

public class DefaultConverter implements ExcelConverter<Object>{
    @Override
    public String convert(Object value) {
        return value != null ? value.toString() : "";
    }
}
