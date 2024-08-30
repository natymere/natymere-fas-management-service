package com.example.fms.service;

import com.example.fms.model.types.EmploymentStatus;
import com.example.fms.model.types.SchoolLevel;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class EligibilityCriteriaService {
    private static final Logger logger = LoggerFactory.getLogger(EligibilityCriteriaService.class);

    private final ObjectMapper objectMapper = new ObjectMapper();

    public boolean tryParseEligibilityCriteria(String eligibilityCriteria) {
        try {
            TypeReference<Map<String, Object>> typeRef = new TypeReference<>() {};
            Map<String, Object> eligibilityCriteriaMap = objectMapper.readValue(eligibilityCriteria, typeRef);
            if (eligibilityCriteriaMap.containsKey("employment_status")) {
                String employmentStatus = (String) eligibilityCriteriaMap.get("employment_status");
                if (employmentStatus == null) return false;
                EmploymentStatus.valueOf(employmentStatus.toUpperCase());
            }

            if (eligibilityCriteriaMap.containsKey("has_children")) {
                @SuppressWarnings("unchecked")
                Map<String, String> schoolLevelMap = (Map<String, String>) eligibilityCriteriaMap.get("has_children");
                if (schoolLevelMap == null) return false;
                String criteriaSchoolLevel = schoolLevelMap.get("school_level");
                if (criteriaSchoolLevel == null) return false;
                SchoolLevel.valueOf(criteriaSchoolLevel.toUpperCase());
            }

            return true;
        } catch (Exception e) {
            logger.error("Unable to parse eligibilityCriteria json", e);
            return false;
        }
    }
}
