package com.example.fms.controller;

import com.example.fms.entity.Applicant;
import com.example.fms.exception.DtoValidationException;
import com.example.fms.mapper.ApplicantMapper;
import com.example.fms.model.ApplicantDto;
import com.example.fms.service.ApplicantService;
import com.example.fms.util.RequestContext;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping(value = "/api/applicants")
public class ApplicantController {

    private static final Logger logger = LoggerFactory.getLogger(ApplicantController.class);
    private final ApplicantService applicantService;
    private final Validator validator;


    public ApplicantController(ApplicantService applicantService, Validator validator) {
        this.applicantService = applicantService;
        this.validator = validator;
    }

    @GetMapping("")
    public ResponseEntity<?> getApplicants() {
        logger.info("Get all applicants");
        List<Applicant> applicants = applicantService.getMainApplicants();

        List<ApplicantDto> results = new ArrayList<>();
        for (Applicant applicant: applicants) {
            results.add(ApplicantMapper.toDtoWithHousehold(applicant));
        }

        logger.info("Retrieved applicant successfully. Time taken: {} ms", RequestContext.getRequestDuration());
        return ResponseEntity.status(HttpStatus.OK).body(results);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getApplicantById(@PathVariable UUID id) {
        logger.info("Get applicant id={}", id);
        Applicant applicant = applicantService.getApplicant(id);
        ApplicantDto result = ApplicantMapper.toDtoWithHousehold(applicant);

        logger.info("Retrieved applicant successfully. Time taken: {} ms", RequestContext.getRequestDuration());
        return ResponseEntity.status(HttpStatus.OK).body(result);
    }


    @PostMapping("")
    public ResponseEntity<?> createApplicant(@RequestBody ApplicantDto applicantDto) {
        logger.info("Create applicant with payload={}", RequestContext.getBody());

        applicantDto.cleanData();
        Set<ConstraintViolation<ApplicantDto>> violations = validator.validate(applicantDto);
        if (!violations.isEmpty()) {
            throw new DtoValidationException(Collections.unmodifiableSet(violations));
        }
        Applicant applicant = applicantService.createApplicant(applicantDto);
        ApplicantDto result = ApplicantMapper.toDtoWithHousehold(applicant);

        logger.info("Created applicant successfully. Time taken: {} ms", RequestContext.getRequestDuration());
        return ResponseEntity.status(HttpStatus.CREATED).body(result);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateApplicant(@PathVariable UUID id, @RequestBody ApplicantDto applicantDto) {
        logger.info("Update applicant with payload={}", RequestContext.getBody());

        applicantDto.cleanData();
        Set<ConstraintViolation<ApplicantDto>> violations = validator.validate(applicantDto);
        if (!violations.isEmpty()) {
            throw new DtoValidationException(Collections.unmodifiableSet(violations));
        }
        Applicant applicant = applicantService.updateApplicant(id, applicantDto);
        ApplicantDto result = ApplicantMapper.toDto(applicant);

        logger.info("Updated applicant successfully. Time taken: {} ms", RequestContext.getRequestDuration());
        return ResponseEntity.status(HttpStatus.OK).body(result);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteApplicant(@PathVariable UUID id) {
        logger.info("Delete applicant id={}", id);

        applicantService.deleteApplicant(id);

        logger.info("Deleted applicant successfully. Time taken: {} ms", RequestContext.getRequestDuration());
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
