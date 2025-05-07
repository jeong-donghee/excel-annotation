package com.github.excel_annotation.aop;

import com.github.excel_annotation.util.ExcelUtil;
import jakarta.servlet.http.HttpServletRequest;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.util.List;
import java.util.Map;

@Aspect
@Component
public class ExcelResponseAspect {

    @Around("@annotation(com.github.excel_annotation.annotation.ExcelResponse)")
    public Object handleExcelResponse(ProceedingJoinPoint joinPoint) throws Throwable {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        String reportParam = request.getParameter("report");

        if (!"true".equalsIgnoreCase(reportParam)) {
            return joinPoint.proceed();
        }

        Object result = joinPoint.proceed();
        Object data;
        if (result instanceof ResponseEntity<?>) {
            data = ((ResponseEntity<?>) result).getBody();
        } else {
            throw new IllegalArgumentException("Expected ResponseEntity as return type");
        }
        List<Map<String, String>> mapList = ExcelUtil.convertToExcelMapList(data);
        byte[] excelBytes = ExcelUtil.generateExcel(mapList);

        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"))
                .header("Content-Disposition", "attachment; filename=report.xlsx")
                .body(excelBytes);
    }
}
