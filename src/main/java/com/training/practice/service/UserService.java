package com.training.practice.service;

import com.training.practice.dto.SubjectCreateDTO;
import com.training.practice.dto.UserCreateDTO;
import com.training.practice.dto.UserDTO;
import com.training.practice.dto.UserUpdateDTO;
import com.training.practice.entity.Subject;
import com.training.practice.entity.User;
import com.training.practice.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class UserService {
    
    private final UserRepository userRepository;
    
    public UserDTO createUser(UserCreateDTO request) {
        log.info("Creating user with email: {}", request.getEmail());
        
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new RuntimeException("Email already exists: " + request.getEmail());
        }
        
        User user = User.builder()
                .name(request.getName())
                .email(request.getEmail())
                .phone(request.getPhone())
                .department(request.getDepartment())
                .status(request.getStatus() != null ? request.getStatus() : User.UserStatus.ACTIVE)
                .build();
        
        User savedUser = userRepository.save(user);
        log.info("User created successfully with ID: {}", savedUser.getId());
        
        return UserDTO.fromEntity(savedUser);
    }
    
    @Transactional(readOnly = true)
    public Optional<UserDTO> getUserById(String id) {
        log.info("Fetching user by ID: {}", id);
        return userRepository.findById(id)
                .map(UserDTO::fromEntity);
    }
    
    @Transactional(readOnly = true)
    public List<UserDTO> getAllUsers() {
        log.info("Fetching all users");
        return userRepository.findAll().stream()
                .map(UserDTO::fromEntity)
                .collect(Collectors.toList());
    }
    
    @Transactional(readOnly = true)
    public Page<UserDTO> getUsersWithPagination(Pageable pageable) {
        log.info("Fetching users with pagination: {}", pageable);
        return userRepository.findAll(pageable)
                .map(UserDTO::fromEntity);
    }
    
    @Transactional(readOnly = true)
    public List<UserDTO> getUsersByDepartment(String department) {
        log.info("Fetching users by department: {}", department);
        return userRepository.findByDepartment(department).stream()
                .map(UserDTO::fromEntity)
                .collect(Collectors.toList());
    }
    
    @Transactional(readOnly = true)
    public List<UserDTO> getUsersByStatus(User.UserStatus status) {
        log.info("Fetching users by status: {}", status);
        return userRepository.findByStatus(status).stream()
                .map(UserDTO::fromEntity)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<UserDTO> getUsersByDepartmentAndStatus(String department, User.UserStatus status) {
        log.info("Fetching users by department: {} and status: {}", department, status);
        return userRepository.findByDepartmentAndStatus(department, status).stream()
                .map(UserDTO::fromEntity)
                .collect(Collectors.toList());
    }
    
    @Transactional(readOnly = true)
    public List<UserDTO> searchUsersByName(String name) {
        log.info("Searching users by name: {}", name);
        return userRepository.findByNameContainingIgnoreCase(name).stream()
                .map(UserDTO::fromEntity)
                .collect(Collectors.toList());
    }
    
    public UserDTO updateUser(String id, UserUpdateDTO request) {
        log.info("Updating user with ID: {}", id);
        
        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found with ID: " + id));
        
        if (request.getName() != null) {
            user.setName(request.getName());
        }
        if (request.getEmail() != null) {
            userRepository.findByEmail(request.getEmail())
                    .ifPresent(existingUser -> {
                        if (!existingUser.getId().equals(id)) {
                            throw new RuntimeException("Email already exists: " + request.getEmail());
                        }
                    });
            user.setEmail(request.getEmail());
        }
        if (request.getPhone() != null) {
            user.setPhone(request.getPhone());
        }
        if (request.getDepartment() != null) {
            user.setDepartment(request.getDepartment());
        }
        if (request.getStatus() != null) {
            user.setStatus(request.getStatus());
        }
        
        User updatedUser = userRepository.save(user);
        log.info("User updated successfully with ID: {}", updatedUser.getId());
        
        return UserDTO.fromEntity(updatedUser);
    }

    public UserDTO addNewSubjectToUser(String userId, SubjectCreateDTO request) {
        log.info("Adding new subject to user with ID: {}", userId);
        
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found with ID: " + userId));
        
        Subject subject = Subject.builder()
                .name(request.getName())
                .description(request.getDescription())
                .code(request.getCode())
                .build();

        subject.setUser(user);
        user.getSubjects().add(subject);
        
        User updatedUser = userRepository.save(user);
        log.info("Subject added successfully to user with ID: {}", updatedUser.getId());
        
        return UserDTO.fromEntity(updatedUser);
    }
    
    public void deleteUser(String id) {
        log.info("Deleting user with ID: {}", id);
        
        if (!userRepository.existsById(id)) {
            throw new RuntimeException("User not found with ID: " + id);
        }
        
        userRepository.deleteById(id);
        log.info("User deleted successfully with ID: {}", id);
    }
    
    @Transactional(readOnly = true)
    public long getUserCountByStatus(User.UserStatus status) {
        log.info("Getting user count by status: {}", status);
        return userRepository.countByStatus(status);
    }
}
