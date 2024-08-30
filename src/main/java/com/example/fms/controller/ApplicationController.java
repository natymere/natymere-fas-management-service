package com.example.fms.controller;


import com.example.fms.entity.Application;
import com.example.fms.model.types.ApplicationStatus;
import com.example.fms.service.ApplicationService;
import com.example.fms.util.RequestContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping(value = "/api/applications")
public class ApplicationController {

    private static final Logger logger = LoggerFactory.getLogger(ApplicationController.class);
    private final ApplicationService applicationService;

    public ApplicationController(ApplicationService applicationService) {
        this.applicationService = applicationService;
    }

    @GetMapping("")
    public ResponseEntity<List<Application>> getApplications() {
        List<Application> applications = applicationService.getAllApplications();
        return ResponseEntity.status(HttpStatus.OK).body(applications);
    }

    @PostMapping("")
    public ResponseEntity<Application> createApplication(
            @RequestParam UUID applicantId, @RequestParam UUID schemeId) {
        logger.info("Creating application");
        Application createdApplication = applicationService.createApplication(applicantId, schemeId);
        logger.info("Created application successfully. Time taken: {} ms", RequestContext.getRequestDuration());
        return ResponseEntity.status(HttpStatus.CREATED).body(createdApplication);
    }

    @PutMapping("status")
    public ResponseEntity<Application> updateApplicationStatus(
            @RequestParam UUID applicationId, @RequestParam ApplicationStatus status) {
        logger.info("Updating application status");
        Application createdApplication = applicationService.updateApplicationStatus(applicationId, status);
        logger.info("Updated application status successfully. Time taken: {} ms", RequestContext.getRequestDuration());
        return ResponseEntity.status(HttpStatus.OK).body(createdApplication);
    }
}
