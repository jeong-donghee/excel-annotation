package com.github.excel_annotation;

import com.github.excel_annotation.aop.AopConfig;
import com.github.excel_annotation.aop.ExcelResponseAspect;
import com.github.excel_annotation.controller.ExcelResponseController;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(controllers = ExcelResponseController.class)
@Import({ExcelResponseAspect.class, AopConfig.class})
public class ExcelResponseTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void withoutReport() throws Exception {
        mockMvc.perform(get("/excel"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));
    }

    @Test
    void withReportFalse() throws Exception {
        mockMvc.perform(get("/excel")
                        .param("report", "false"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));
    }

    @Test
    void withReportTrue() throws Exception {
        mockMvc.perform(get("/excel")
                        .param("report", "true"))
                .andExpect(status().isOk())
                .andExpect(header().string("Content-Disposition", Matchers.containsString("attachment")))
                .andExpect(content().contentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"));
    }
}
