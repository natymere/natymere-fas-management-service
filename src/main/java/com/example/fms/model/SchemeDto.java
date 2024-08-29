package com.example.fms.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Map;
import java.util.UUID;


@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class SchemeDto {
    private UUID id;
    private String name;
    private String description;
    private Map<String, Object> schemeCriteria;
    private List<BenefitDto> benefits;
}
