package com.example.fms.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.UUID;


@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class BenefitDto {
   private UUID id;
   private String name;
   private String description;
   private BigDecimal amount;
   private BigDecimal percentage;
}
