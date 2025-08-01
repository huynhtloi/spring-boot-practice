package com.training.practice.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import com.training.practice.client.PostmanClient;
import com.training.practice.dto.backend.PostmanRequest;
import com.training.practice.dto.backend.PostmanResponse;
import com.training.practice.dto.external.PostmanClientResponse;
import com.training.practice.mapper.PostmanClientMapper;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class PostmanClientService {
    
    private final PostmanClient postmanClient;
    private final PostmanClientMapper postmanClientMapper;
    
    public List<PostmanResponse> getAllUsers() {
        log.info("Fetching all users from Postman mock API");
        List<PostmanClientResponse> response = postmanClient.getAllUsers();
        return response.stream()
                .map(postmanClientMapper::toBeInternal)
                .collect(Collectors.toList());
    }
    
    public PostmanResponse getUserById(Long id) {
        log.info("Fetching user by ID: {}", id);
        return postmanClientMapper.toBeInternal(postmanClient.getUserById(id));
    }
    
    public List<PostmanResponse> getUsersByRole(String role) {
        log.info("Fetching users by role: {}", role);
        List<PostmanClientResponse> response = postmanClient.getUsersByRole(role);
        return postmanClientMapper.toBeInternals(response);
    }
    
    public PostmanResponse createUser(PostmanRequest request) {
        log.info("Creating new user: {}", request.getUserPostmanName());
        log.info("Request details: {}", request);
        return postmanClientMapper.toBeInternal(postmanClient.createUser(request));
    }
    
    public PostmanResponse updateFullUser(Long id, PostmanRequest request) {
        log.info("Updating full user: {}", id);
        log.info("Request details: {}", request);
        return postmanClientMapper.toBeInternal(postmanClient.updateFullUser(id, request));
    }
    
    public PostmanResponse updatePartialUser(Long id, PostmanRequest request) {
        log.info("Partially updating user: {}", id);
        log.info("Request details: {}", request);
        return postmanClientMapper.toBeInternal(postmanClient.updatePartialUser(id, request));
    }
    
    public void deleteUser(Long id) {
        log.info("Deleting user: {}", id);
        postmanClient.deleteUser(id);
    }
    
    public List<String> getUserPermissions(Long id) {
        log.info("Fetching permissions for user: {}", id);
        return postmanClient.getUserPermissions(id);
    }
    
    public PostmanResponse assignRole(Long id, String role) {
        log.info("Assigning role {} to user: {}", role, id);
        return postmanClientMapper.toBeInternal(postmanClient.assignRole(id, role));
    }
}
