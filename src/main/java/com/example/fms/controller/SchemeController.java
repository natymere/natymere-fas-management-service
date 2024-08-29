package com.example.fms.controller;

import com.example.fms.entity.Scheme;
import com.example.fms.mapper.SchemeMapper;
import com.example.fms.model.SchemeDto;
import com.example.fms.service.SchemeService;
import com.example.fms.util.RequestContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping(value = "/api/schemes")
public class SchemeController {

    private static final Logger logger = LoggerFactory.getLogger(SchemeController.class);
    private final SchemeService schemeService;

    public SchemeController(SchemeService schemeService) {
        this.schemeService = schemeService;
    }

    @GetMapping("")
    public ResponseEntity<?> getSchemes() {
        logger.info("Get all schemes");
        List<Scheme> schemes = schemeService.getAllSchemes();

        List<SchemeDto> results = new ArrayList<>();
        for (Scheme s: schemes) {
            results.add(SchemeMapper.toDtoWithBenefits(s));
        }

        logger.info("Retrieved schemes successfully. Time taken: {} ms", RequestContext.getRequestDuration());
        return ResponseEntity.status(HttpStatus.OK).body(results);
    }

    @GetMapping("/eligible")
    public ResponseEntity<?> getEligibleSchemes(@RequestParam("applicant") UUID applicantId) {
        logger.info("Get available schemes for applicantId: " + applicantId);
        List<Scheme> schemes = schemeService.getEligibleSchemes(applicantId);

        List<SchemeDto> results = new ArrayList<>();
        for (Scheme s: schemes) {
            results.add(SchemeMapper.toDtoWithBenefits(s));
        }

        logger.info("Retrieved schemes successfully. Time taken: {} ms", RequestContext.getRequestDuration());
        return ResponseEntity.status(HttpStatus.OK).body(results);
    }
}
