package com.training.practice.service;

import com.training.practice.dto.SubjectDTO;
import com.training.practice.dto.SubjectUpdateDTO;
import com.training.practice.entity.Subject;
import com.training.practice.exception.SubjectNotFoundException;
import com.training.practice.mapper.SubjectMapper;
import com.training.practice.repository.SubjectRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Slf4j
@Transactional
@RequiredArgsConstructor
public class SubjectService {
    private final SubjectRepository subjectRepository;
    private final SubjectMapper subjectMapper;

    @Transactional(readOnly = true)
    public Optional<SubjectDTO> getSubjectById(String id) {
        log.info("Fetching subject by ID: {}", id);
        return subjectRepository.findById(id).map(subjectMapper::toDTO);
    }

    @Transactional(readOnly = true)
    public Page<SubjectDTO> getSubjectsWithPagination(Pageable pageable) {
        log.info("Fetching subjects with pagination: {}", pageable);
        return subjectRepository.findAll(pageable)
                .map(subjectMapper::toDTO);
    }

    @Transactional(readOnly = true)
    public List<SubjectDTO> searchSubjectByName(String name, int limit) {
        log.info("Searching subjects by name: {}", name);
        return subjectRepository.findByNameContainingIgnoreCase(name, PageRequest.of(0, limit)).stream()
                .map(subjectMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Transactional
    public SubjectDTO updateSubject(String id, SubjectUpdateDTO request) {
        log.info("Updating subject with ID: {}", id);
        Subject subject = subjectRepository.findById(id)
                .orElseThrow(() -> SubjectNotFoundException.withId(id));

        subjectMapper.updateEntityFromUpdateDTO(request, subject);

        Subject updatedSubject = subjectRepository.save(subject);
        log.info("Subject updated successfully with ID: {}", updatedSubject.getId());

        return subjectMapper.toDTO(updatedSubject);
    }
}
