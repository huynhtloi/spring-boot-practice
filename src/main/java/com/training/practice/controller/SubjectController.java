package com.training.practice.controller;

import com.training.practice.dto.ApiResponseDTO;
import com.training.practice.dto.SubjectDTO;
import com.training.practice.dto.SubjectUpdateDTO;
import com.training.practice.service.FeatureToggleService;
import com.training.practice.service.SubjectService;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping(value = "/api/subjects")
@RequiredArgsConstructor
@Slf4j
@Validated
public class SubjectController {
    private final SubjectService subjectService;
    private final FeatureToggleService featureToggleService;

    @GetMapping("/{id}")
    @ResponseBody
    public ResponseEntity<ApiResponseDTO<SubjectDTO>> getSubjectById(
            @PathVariable String id,
            @RequestHeader(value = "X-Request-ID", required = false) String requestId) {

        log.info("Fetching subject by ID: {} with request ID: {}", id, requestId);

        return subjectService.getSubjectById(id)
                .map(subject -> {
                    ApiResponseDTO<SubjectDTO> response = ApiResponseDTO.success(subject);
                    response.setRequestId(requestId != null ? requestId : UUID.randomUUID().toString());
                    return ResponseEntity.ok(response);
                })
                .orElseGet(() -> {
                    ApiResponseDTO<SubjectDTO> response = ApiResponseDTO.error("Subject not found with ID: " + id);
                    response.setRequestId(requestId != null ? requestId : UUID.randomUUID().toString());
                    return ResponseEntity.notFound().build();
                });
    }

    @GetMapping
    @ResponseBody
    public ResponseEntity<ApiResponseDTO<Page<SubjectDTO>>> getAllSubjects(
            @RequestParam(value = "page", defaultValue = "0") @Min(0) int page,
            @RequestParam(value = "size", required = false) Integer size,
            @RequestParam(value = "sort", defaultValue = "id") String sortBy,
            @RequestParam(value = "direction", defaultValue = "ASC") String sortDirection,
            @RequestHeader(value = "X-Request-ID", required = false) String requestId) {

        int pageSize = size != null ? Math.min(size, featureToggleService.getMaxPageSize()) : featureToggleService.getDefaultPageSize();

        log.info("Fetching all subjects with page: {}, size: {}, sortBy: {}, sortDirection: {}, requestId: {}",
                page, size, sortBy, sortDirection, requestId);

        try {
            Sort.Direction direction = Sort.Direction.fromString(sortDirection);
            Pageable pageable = PageRequest.of(page, pageSize, Sort.by(direction, sortBy));

            Page<SubjectDTO> subjects = subjectService.getSubjectsWithPagination(pageable);
            ApiResponseDTO<Page<SubjectDTO>> response = ApiResponseDTO.success(subjects);
            response.setRequestId(requestId != null ? requestId : UUID.randomUUID().toString());

            return ResponseEntity.ok(response);
        } catch (Exception e) {
            log.error("Error fetching subjects: {}", e.getMessage());
            ApiResponseDTO<Page<SubjectDTO>> response = ApiResponseDTO.error(e.getMessage());
            response.setRequestId(requestId != null ? requestId : UUID.randomUUID().toString());
            return ResponseEntity.badRequest().body(response);
        }
    }

    @GetMapping("/search")
    @ResponseBody
    public ResponseEntity<ApiResponseDTO<List<SubjectDTO>>> searchSubjects(
            @RequestParam("q") String query,
            @RequestParam(value = "limit", defaultValue = "50") @Min(1) @Max(100) int limit,
            @RequestHeader(value = "X-Request-ID", required = false) String requestId) {

        log.info("Searching subjects with query: {} and limit: {} with request ID: {}", query, limit, requestId);

        List<SubjectDTO> subjects = subjectService.searchSubjectByName(query, limit);

        ApiResponseDTO<List<SubjectDTO>> response = ApiResponseDTO.success(subjects);
        response.setRequestId(requestId != null ? requestId : UUID.randomUUID().toString());

        return ResponseEntity.ok(response);
    }

    @PutMapping("/{id}")
    @ResponseBody
    public ResponseEntity<ApiResponseDTO<SubjectDTO>> updateSubject(
            @PathVariable String id,
            @RequestBody SubjectUpdateDTO request,
            @RequestHeader(value = "X-Request-ID", required = false) String requestId) {
        log.info("Updating subject with ID: {} with request ID: {}", id, requestId);

        try {
            SubjectDTO subjectResponse = subjectService.updateSubject(id, request);
            ApiResponseDTO<SubjectDTO> response = ApiResponseDTO.success("Subject updated successfully", subjectResponse);
            response.setRequestId(requestId != null ? requestId : UUID.randomUUID().toString());
            return ResponseEntity.ok(response);
        } catch (RuntimeException e) {
            log.error("Error updating subject: {}", e.getMessage());
            ApiResponseDTO<SubjectDTO> response = ApiResponseDTO.error(e.getMessage());
            response.setRequestId(requestId != null ? requestId : UUID.randomUUID().toString());
            return ResponseEntity.badRequest().body(response);
        }
    }
}
