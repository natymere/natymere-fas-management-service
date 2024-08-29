package com.example.fms.entity;

import com.example.fms.model.types.EmploymentStatus;
import com.example.fms.model.types.Gender;
import com.example.fms.model.types.MaritalStatus;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@EqualsAndHashCode(exclude = "householdMembers")
@Builder
@ToString(exclude = "householdMembers")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "applicants")
public class Applicant {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id", updatable = false, nullable = false)
    private UUID id;

    @Column(name = "name", nullable = false)
    private String name;

    @Enumerated(EnumType.STRING)
    @Column(name = "employment_status", nullable = false)
    private EmploymentStatus employmentStatus;

    @Enumerated(EnumType.STRING)
    @Column(name = "marital_status", nullable = false)
    private MaritalStatus maritalStatus;

    @Enumerated(EnumType.STRING)
    @Column(name = "gender", nullable = false)
    private Gender gender;

    @Column(name = "dob", nullable = false)
    private LocalDate dob;

    @Column(name = "school_level")
    private String schoolLevel;

    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt = LocalDateTime.now();

    @Column(name = "updated_at")
    private LocalDateTime updatedAt = LocalDateTime.now();

    @Column(name = "deleted_at")
    private LocalDateTime deletedAt;

    @JsonManagedReference
    @OneToMany(mappedBy = "applicant", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<HouseholdMember> householdMembers = new HashSet<>();

    public Applicant(String name, MaritalStatus maritalStatus, EmploymentStatus employmentStatus, Gender gender, LocalDate dob) {
        this.name = name;
        this.maritalStatus = maritalStatus;
        this.employmentStatus = employmentStatus;
        this.gender = gender;
        this.dob = dob;
    }

    public void markAsDeleted() {
        this.deletedAt = LocalDateTime.now();
    }

    public boolean isDeleted() {
        return this.deletedAt != null;
    }

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
        updatedAt = LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
    }
}
