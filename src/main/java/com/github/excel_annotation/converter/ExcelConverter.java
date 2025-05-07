package com.github.excel_annotation.converter;

public interface ExcelConverter<T> {
    String convert(T value);
}
