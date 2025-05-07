package com.github.excel_annotation.controller;

import com.github.excel_annotation.annotation.ExcelResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import java.time.LocalDateTime;

@Controller
public class ExcelResponseController {

    @GetMapping("/excel")
    @ExcelResponse
    public ResponseEntity<ExcelDto> getExcelResponse() {
        return ResponseEntity.ok(ExcelDto.builder()
                .field1("Value1")
                .field2("Value2")
                .field3("Value3")
                .field4(LocalDateTime.now())
                .build());
    }
}
