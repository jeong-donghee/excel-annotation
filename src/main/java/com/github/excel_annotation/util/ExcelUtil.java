package com.github.excel_annotation.util;

import com.github.excel_annotation.annotation.ExcelIgnore;
import com.github.excel_annotation.annotation.ExcelProperty;
import com.github.excel_annotation.converter.DefaultConverter;
import com.github.excel_annotation.converter.ExcelConverter;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class ExcelUtil {
    public static List<Map<String, String>> convertToExcelMapList(Object data) {
        List<Map<String, String>> result = new ArrayList<>();

        if (data instanceof List<?>) {
            for (Object dto : (List<?>) data) {
                result.add(dtoToExcelMap(dto));
            }
        } else {
            result.add(dtoToExcelMap(data));
        }

        return result;
    }

    private static Map<String, String> dtoToExcelMap(Object dto) {
        Map<String, String> result = new LinkedHashMap<>();

        for (Field field : dto.getClass().getDeclaredFields()) {
            field.setAccessible(true);

            if (field.isAnnotationPresent(ExcelIgnore.class)) continue;

            String header = field.getName();
            ExcelConverter<Object> converter = new DefaultConverter();

            if (field.isAnnotationPresent(ExcelProperty.class)) {
                ExcelProperty prop = field.getAnnotation(ExcelProperty.class);
                if (!prop.name().isEmpty()) header = prop.name();
                try {
                    converter = (ExcelConverter<Object>) prop.convert().getDeclaredConstructor().newInstance();
                } catch (NoSuchMethodException | InvocationTargetException | InstantiationException | IllegalAccessException ignore) {
                }
            }

            Object value;
            try {
                value = field.get(dto);
            } catch (IllegalAccessException e) {
                value = "";
            }
            result.put(header, converter.convert(value));
        }

        return result;
    }

    public static byte[] generateExcel(List<Map<String, String>> data) throws IOException {
        try (Workbook workbook = new XSSFWorkbook()) {
            Sheet sheet = workbook.createSheet("Sheet1");

            if (data.isEmpty()) return new byte[0];

            // Header
            Row headerRow = sheet.createRow(0);
            int col = 0;
            for (String key : data.get(0).keySet()) {
                headerRow.createCell(col++).setCellValue(key);
            }

            // Rows
            int rowIdx = 1;
            for (Map<String, String> row : data) {
                Row dataRow = sheet.createRow(rowIdx++);
                int cellIdx = 0;
                for (String val : row.values()) {
                    dataRow.createCell(cellIdx++).setCellValue(val);
                }
            }

            ByteArrayOutputStream out = new ByteArrayOutputStream();
            workbook.write(out);
            return out.toByteArray();
        }
    }
}
