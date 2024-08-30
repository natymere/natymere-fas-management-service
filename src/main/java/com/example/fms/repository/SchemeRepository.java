package com.example.fms.repository;

import com.example.fms.entity.Scheme;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface SchemeRepository extends JpaRepository<Scheme, UUID> {

}

