package com.github.excel_annotation.controller;

import com.github.excel_annotation.annotation.ExcelIgnore;
import com.github.excel_annotation.annotation.ExcelProperty;
import com.github.excel_annotation.converter.LocalDateTimeConverter;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Builder
@Getter
public class ExcelDto {
    private String field1;
    @ExcelIgnore
    private String field2;
    @ExcelProperty(name = "field3_1")
    private String field3;
    @ExcelProperty(convert = LocalDateTimeConverter.class)
    private LocalDateTime field4;
}
