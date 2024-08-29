package com.example.fms.model;

import com.example.fms.model.types.EmploymentStatus;
import com.example.fms.model.types.Gender;
import com.example.fms.model.types.MaritalStatus;
import com.example.fms.validation.DataCleaner;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ApplicantDto {
    private UUID id;

    @NotBlank(message = "Name is required")
    @Size(max = 50, message = "Name cannot exceed 50 characters")
    private String name;

    @JsonProperty("employment_status")
    private EmploymentStatus employmentStatus;

    @JsonProperty("marital_status")
    private MaritalStatus maritalStatus;

    private Gender sex;

    private LocalDate dob;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String relation;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private List<ApplicantDto> household;

    public void cleanData() {
        this.name = DataCleaner.cleanString(this.name);
        this.relation = DataCleaner.cleanString(this.relation);
    }
}
