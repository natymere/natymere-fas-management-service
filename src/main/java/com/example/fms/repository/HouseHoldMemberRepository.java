package com.example.fms.repository;

import com.example.fms.entity.HouseholdMember;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Set;
import java.util.UUID;

@Repository
public interface HouseHoldMemberRepository extends JpaRepository<HouseholdMember, UUID> {

    @Query("SELECT hm FROM HouseholdMember hm WHERE hm.applicant.id = :applicantId AND hm.householdMember.deletedAt IS NULL")
    Set<HouseholdMember> findByApplicantId(@Param("applicantId") UUID applicantId);
}
