package com.example.fms.repository;

import com.example.fms.entity.Application;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface ApplicationRepository extends JpaRepository<Application, UUID> {

    @Query("SELECT COUNT(hm) > 0 FROM HouseholdMember hm WHERE hm.householdMember.id = :householdMemberId")
    boolean existsByHouseholdMemberId(@Param("householdMemberId") UUID householdMemberId);

}
