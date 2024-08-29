package com.example.fms.repository;

import com.example.fms.entity.Scheme;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface SchemeRepository extends JpaRepository<Scheme, UUID> {

}

