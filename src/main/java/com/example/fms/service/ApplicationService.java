package com.example.fms.service;

import com.example.fms.entity.Applicant;
import com.example.fms.entity.Application;
import com.example.fms.entity.Scheme;
import com.example.fms.exception.ResourceNotFoundException;
import com.example.fms.model.types.ApplicationStatus;
import com.example.fms.repository.ApplicantRepository;
import com.example.fms.repository.ApplicationRepository;
import com.example.fms.repository.SchemeRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
public class ApplicationService {
    private static final Logger logger = LoggerFactory.getLogger(ApplicationService.class);

    private final ApplicantRepository applicantRepository;
    private final SchemeService schemeService;
    private final SchemeRepository schemeRepository;
    private final ApplicationRepository applicationRepository;

    public ApplicationService(ApplicantRepository applicantRepository,
                              SchemeService schemeService,
                              SchemeRepository schemeRepository,
                              ApplicationRepository applicationRepository) {
        this.applicantRepository = applicantRepository;
        this.schemeRepository = schemeRepository;
        this.applicationRepository = applicationRepository;
        this.schemeService = schemeService;
    }

    public List<Application> getAllApplications() {
        return this.applicationRepository.findAll();
    }


    @Transactional
    public Application createApplication(UUID applicantId, UUID schemeId) {
        // 1. find applicant and scheme from the repository and validate if applicant is household member
        Applicant applicant = applicantRepository.findById(applicantId)
                .orElseThrow(() -> {
                    logger.error("Applicant not found with id: {}", applicantId);
                    return new ResourceNotFoundException("Applicant not found with id: " + applicantId);
                });

        Scheme scheme = schemeRepository.findById(schemeId)
                .orElseThrow(() -> {
                    logger.error("Scheme not found with id: {}", schemeId);
                    return new ResourceNotFoundException("Scheme not found with id: " + schemeId);
                });

        if (isHouseholdMember(applicant)) {
            logger.error("Applicant is a household member and not the main applicant with id: {}", applicantId);
            throw new IllegalArgumentException("Applicant is not eligible for the selected scheme.");
        }

        // 2. check if the applicant is eligible for the scheme
        if (!schemeService.isEligible(applicant, scheme)) {
            throw new IllegalArgumentException("Applicant is not eligible for the selected scheme. applicantId: " + applicantId);
        }

        // 3. create the application
        Application application = Application.builder()
                .applicant(applicant)
                .scheme(scheme)
                .applicationDate(LocalDateTime.now())
                .status(ApplicationStatus.PENDING)
                .build();

        // 4. save the application
        return applicationRepository.save(application);
    }

    public Application updateApplicationStatus(UUID id, ApplicationStatus status) {
        Application application = applicationRepository.findById(id).orElseThrow(() -> {
            logger.error("Application not found with id: {}", id);
            return new ResourceNotFoundException("Application not found with id: " + id);
        });

        application.setStatus(status);
        return applicationRepository.save(application);
    }

    // check if it is a household member
    private boolean isHouseholdMember(Applicant applicant) {
        return applicationRepository.existsByHouseholdMemberId(applicant.getId());
    }
}
