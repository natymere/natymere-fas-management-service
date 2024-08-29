package com.example.fms.repository;

import com.example.fms.entity.Applicant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface ApplicantRepository extends JpaRepository<Applicant, UUID> {

    @Query("SELECT a FROM Applicant a LEFT JOIN FETCH a.householdMembers hm WHERE a.id = :id")
    Optional<Applicant> findHouseholdMembersById(@Param("id") UUID id);

    @Query("SELECT DISTINCT a FROM Applicant a " +
            "LEFT JOIN FETCH a.householdMembers hm " +
            "WHERE a.id NOT IN (SELECT hm.householdMember.id FROM HouseholdMember hm)")
    List<Applicant> findAllMainApplicants();
}
