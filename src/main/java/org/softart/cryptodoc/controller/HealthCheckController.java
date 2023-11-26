package org.softart.cryptodoc.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/api/v1/health-check")
public class HealthCheckController {
    @GetMapping
    public ResponseEntity<Object> healthCheck() {
        return ResponseEntity.ok().body(Map.of("message", "ok"));
    }
}
