package com.example.fms.mapper;

import com.example.fms.entity.Benefit;
import com.example.fms.entity.Scheme;
import com.example.fms.model.BenefitDto;
import com.example.fms.model.SchemeDto;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class SchemeMapper {
    public static SchemeDto toDtoWithBenefits(Scheme scheme) {
        if (scheme == null) return null;

        List<Benefit> benefits = scheme.getBenefits();
        List<BenefitDto> benefitsDto = new ArrayList<>();
        if (benefits != null) {
            benefits.forEach(x -> benefitsDto.add(toBenefitDto(x)));
        }

        SchemeDto schemeDto = SchemeDto.builder()
                .id(scheme.getId())
                .name(scheme.getName())
                .description(scheme.getDescription())
                .benefits(benefitsDto)
                .build();

        try {
            String eligibilityCriteria = scheme.getEligibilityCriteria();
            Map<String, Object> criteriaMap = convertCriteriaStringToMap(eligibilityCriteria);
            schemeDto.setSchemeCriteria(criteriaMap);
        } catch (Exception e) {
            throw new RuntimeException("Failed to parse scheme eligibility criteria JSON", e);
        }
        return schemeDto;
    }

    public static Map<String, Object> convertCriteriaStringToMap(String criteria) {
        Map<String, Object> result;
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            TypeReference<Map<String, Object>> typeRef = new TypeReference<>() {};
            result = objectMapper.readValue(criteria, typeRef);
        } catch (Exception e) {
            throw new RuntimeException("Failed to parse scheme eligibility criteria JSON", e);
        }
        return result;
    }

    public static BenefitDto toBenefitDto(Benefit benefit) {
        return BenefitDto.builder()
                .id(benefit.getId())
                .name(benefit.getName())
                .description(benefit.getDescription())
                .amount(benefit.getAmount())
                .percentage(benefit.getPercentage())
                .build();
    }
}
