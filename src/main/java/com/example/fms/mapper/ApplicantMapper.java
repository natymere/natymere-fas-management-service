package com.example.fms.mapper;

import com.example.fms.entity.Applicant;
import com.example.fms.entity.HouseholdMember;
import com.example.fms.model.ApplicantDto;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class ApplicantMapper {
    public static ApplicantDto toDtoWithHousehold(Applicant applicant) {
        List<ApplicantDto> householdMembersDto = applicant.getHouseholdMembers().stream()
                .map(hm -> ApplicantDto.builder()
                        .id(hm.getHouseholdMember().getId())
                        .name(hm.getHouseholdMember().getName())
                        .employmentStatus(hm.getHouseholdMember().getEmploymentStatus())
                        .sex(hm.getHouseholdMember().getGender())
                        .dob(hm.getHouseholdMember().getDob())
                        .relation(hm.getRelation())
                        .maritalStatus(hm.getHouseholdMember().getMaritalStatus())
                        .build())
                .toList();


        return ApplicantDto.builder()
                .id(applicant.getId())
                .name(applicant.getName())
                .sex(applicant.getGender())
                .dob(applicant.getDob())
                .household(householdMembersDto)
                .employmentStatus(applicant.getEmploymentStatus())
                .maritalStatus(applicant.getMaritalStatus())
                .build();
    }

    public static ApplicantDto toDto(Applicant applicant) {
        return ApplicantDto.builder()
                .id(applicant.getId())
                .name(applicant.getName())
                .sex(applicant.getGender())
                .dob(applicant.getDob())
                .employmentStatus(applicant.getEmploymentStatus())
                .maritalStatus(applicant.getMaritalStatus())
                .build();
    }

    public static Applicant toEntityWithHousehold(ApplicantDto applicantDto) {
        if (applicantDto.getHousehold() == null) {
            applicantDto.setHousehold(new ArrayList<>());
        }

        Applicant applicant = Applicant.builder()
                .name(applicantDto.getName())
                .gender(applicantDto.getSex())
                .dob(applicantDto.getDob())
                .employmentStatus(applicantDto.getEmploymentStatus())
                .maritalStatus(applicantDto.getMaritalStatus())
                .build();

        Set<HouseholdMember> householdMembers = new HashSet<>();

        for (ApplicantDto member: applicantDto.getHousehold()) {
            HouseholdMember householdMember = HouseholdMember.builder()
                    .applicant(applicant)
                    .householdMember(toEntity(member))
                    .relation(member.getRelation())
                    .build();
            householdMembers.add(householdMember);
        }

        applicant.setHouseholdMembers(householdMembers);
        return applicant;
    }

    public static Applicant toEntity(ApplicantDto applicantDto) {
        return Applicant.builder()
                .name(applicantDto.getName())
                .gender(applicantDto.getSex())
                .dob(applicantDto.getDob())
                .employmentStatus(applicantDto.getEmploymentStatus())
                .maritalStatus(applicantDto.getMaritalStatus())
                .build();
    }
}
