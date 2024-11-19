package com.example.goldenraspberryawards.controller;

import com.example.goldenraspberryawards.service.ProducerService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/producers")
public class ProducerController {

    private final ProducerService producerService;

    public ProducerController(ProducerService producerService) {
        this.producerService = producerService;
    }

    @GetMapping("/interval")
    public Map<String, List<Map<String, Object>>> getProducersIntervals() {
        return producerService.getProducersIntervals();
    }
}
