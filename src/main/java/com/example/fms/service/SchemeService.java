package com.example.fms.service;

import com.example.fms.entity.Applicant;
import com.example.fms.entity.HouseholdMember;
import com.example.fms.entity.Scheme;
import com.example.fms.exception.ResourceNotFoundException;
import com.example.fms.repository.ApplicantRepository;
import com.example.fms.repository.SchemeRepository;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class SchemeService {
    private static final Logger logger = LoggerFactory.getLogger(SchemeService.class);

    private final SchemeRepository schemeRepository;
    private final ApplicantRepository applicantRepository;
    private final ObjectMapper objectMapper = new ObjectMapper();

    public SchemeService(SchemeRepository schemeRepository, ApplicantRepository applicantRepository) {
        this.schemeRepository = schemeRepository;
        this.applicantRepository = applicantRepository;
    }

    public List<Scheme> getAllSchemes() {
        return schemeRepository.findAll();
    }

    public List<Scheme> getEligibleSchemes(UUID applicantId) {
        Applicant applicant = applicantRepository.findById(applicantId).orElseThrow(() -> {
            logger.error("Applicant not found with id: {}", applicantId);
            return new ResourceNotFoundException("Applicant not found. id=" + applicantId);
        });

        if (applicant.isDeleted()) {
            logger.warn("Applicant with id: {} is found but deleted", applicantId);
            throw new ResourceNotFoundException("Applicant not found. id=" + applicantId);
        }

        List<Scheme> allSchemes = schemeRepository.findAll();
        return allSchemes.stream()
                .filter(scheme -> isEligible(applicant, scheme))
                .collect(Collectors.toList());
    }

    private boolean isEligible(Applicant applicant, Scheme scheme) {
        try {
            TypeReference<Map<String, Object>> typeRef = new TypeReference<>() {};
            Map<String, Object> eligibilityCriteria = objectMapper.readValue(scheme.getEligibilityCriteria(), typeRef);

            if (eligibilityCriteria.containsKey("employment_status")) {
                String employmentStatus = (String) eligibilityCriteria.get("employment_status");
                if (!applicant.getEmploymentStatus().name().equals(employmentStatus)) {
                    return false;
                }
            }

            if (eligibilityCriteria.containsKey("has_children")) {
                @SuppressWarnings("unchecked")
                Map<String, String> schoolLevelMap = (Map<String, String>) eligibilityCriteria.get("has_children");
                String criteriaSchoolLevel = schoolLevelMap.get("school_level");
                Set<HouseholdMember> householdMembers = applicant.getHouseholdMembers();
                boolean pass = false;
                for (HouseholdMember hm : householdMembers) {
                    Applicant householdMember = hm.getHouseholdMember();
                    String householdMemberSchoolLevel = householdMember.getSchoolLevel();
                    if (householdMemberSchoolLevel != null && householdMemberSchoolLevel.equals(criteriaSchoolLevel)) {
                        pass = true;
                        break;
                    }
                }
                if (!pass) return false;
            }

            return true;
        } catch (Exception e) {
            throw new RuntimeException("Failed to parse scheme eligibility criteria JSON", e);
        }
    }
}
