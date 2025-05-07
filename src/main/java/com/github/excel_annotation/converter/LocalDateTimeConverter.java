package com.github.excel_annotation.converter;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class LocalDateTimeConverter implements ExcelConverter<LocalDateTime>{
    @Override
    public String convert(LocalDateTime value) {
        return value != null ? value.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")) : "";
    }
}
