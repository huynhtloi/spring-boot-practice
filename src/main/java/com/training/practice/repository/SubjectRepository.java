package com.training.practice.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import com.training.practice.entity.Subject;

@Repository
public interface SubjectRepository extends JpaRepository<Subject, String> {
    Optional<Subject> findByName(String name);
    List<Subject> findByCode(String code);
    List<Subject> findByDescriptionContainingIgnoreCase(String description);
    List<Subject> findByNameContainingIgnoreCase(String name, Pageable pageable);

}
