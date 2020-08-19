package com.vtb.vladislav.spring.data.lesson8.homework.controllers.rest;

import com.vtb.vladislav.spring.data.lesson8.homework.beans.LogAspect;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/api/v1/stat")
@AllArgsConstructor
public class LogAspectController {
    private LogAspect logAspect;

    @GetMapping
    public Map<String, Map<String, Integer>> getStatistics() {
        return logAspect.getStatistics();
    }
}
