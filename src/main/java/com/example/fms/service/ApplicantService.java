package com.example.fms.service;

import com.example.fms.entity.Applicant;
import com.example.fms.entity.HouseholdMember;
import com.example.fms.exception.ResourceNotFoundException;
import com.example.fms.mapper.ApplicantMapper;
import com.example.fms.model.ApplicantDto;
import com.example.fms.model.types.HouseholdRelationship;
import com.example.fms.repository.ApplicantRepository;
import com.example.fms.repository.HouseHoldMemberRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
public class ApplicantService {

    private static final Logger logger = LoggerFactory.getLogger(ApplicantService.class);
    private final ApplicantRepository applicantRepository;
    private final HouseHoldMemberRepository houseHoldMemberRepository;

    public ApplicantService(ApplicantRepository applicantRepository, HouseHoldMemberRepository houseHoldMemberRepository) {
        this.applicantRepository = applicantRepository;
        this.houseHoldMemberRepository = houseHoldMemberRepository;
    }

    public List<Applicant> getMainApplicants() {
        return applicantRepository.findAllMainApplicants();
    }

    public Applicant getApplicant(UUID id) {
        // 1. find main applicant
        Applicant applicant = applicantRepository.findById(id).orElseThrow(() -> {
            logger.error("Applicant not found with id: {}", id);
            return new ResourceNotFoundException("Applicant not found. id=" + id);
        });
        if (applicant.isDeleted()) {
            logger.warn("Applicant with id: {} is found but deleted", id);
            throw new ResourceNotFoundException("Applicant not found. id=" + id);
        }

        // 2. find household members that are not soft deleted
        Set<HouseholdMember> householdMembers = houseHoldMemberRepository.findByApplicantId(id);
        applicant.setHouseholdMembers(householdMembers);

        return applicant;
    }

    @Transactional
    public Applicant createApplicant(ApplicantDto applicantDto) {
        // 1. clean data and perform validation
        if (applicantDto.getHousehold() == null) {
            applicantDto.setHousehold(new ArrayList<>());
        }
        List<ApplicantDto> householdMembersDto = applicantDto.getHousehold();
        householdMembersDto.forEach(this::validateHouseholdMemberAttributes);

        // 2. convert to applicant entity and save
        Applicant applicant = ApplicantMapper.toEntity(applicantDto);
        Applicant savedApplicant = applicantRepository.save(applicant);

        // 3. save household applicants
        Set<HouseholdMember> householdMembers = new HashSet<>();
        for (ApplicantDto memberDto : householdMembersDto) {
            Applicant householdApplicant = ApplicantMapper.toEntity(memberDto);
            Applicant savedHouseholdApplicant = applicantRepository.save(householdApplicant);

            HouseholdMember householdMember = HouseholdMember.builder()
                    .applicant(savedApplicant)
                    .householdMember(savedHouseholdApplicant)
                    .relation(memberDto.getRelation())
                    .build();
            householdMembers.add(householdMember);
        }
        // 4. save all household members
        List<HouseholdMember> savedHouseholdMembers = houseHoldMemberRepository.saveAll(householdMembers);
        savedApplicant.setHouseholdMembers(new HashSet<>(savedHouseholdMembers));
        return savedApplicant;
    }

    public Applicant updateApplicant(UUID id, ApplicantDto applicantDto) {
        Applicant applicant = applicantRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Applicant not found. id=" + id));
        updateApplicantDetails(applicant, applicantDto);
        return applicantRepository.save(applicant);
    }

    public void deleteApplicant(UUID id) {
        Applicant applicant = applicantRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Applicant not found. id=" + id));
        applicant.markAsDeleted();
        applicantRepository.save(applicant);
    }

    private void validateHouseholdMemberAttributes(ApplicantDto householdMember) {
        String relation = householdMember.getRelation();
        if (relation == null || relation.trim().isEmpty()) throw new IllegalArgumentException("Relation property is null or empty");
        try {
            HouseholdRelationship.valueOf(relation.trim().toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Invalid household relation:" + relation);
        }
    }

    private void updateApplicantDetails(Applicant applicant, ApplicantDto applicantDto) {
        applicant.setName(applicantDto.getName());
        applicant.setDob(applicantDto.getDob());
        applicant.setGender(applicantDto.getSex());
        applicant.setEmploymentStatus(applicantDto.getEmploymentStatus());
        applicant.setMaritalStatus(applicantDto.getMaritalStatus());
    }
}
