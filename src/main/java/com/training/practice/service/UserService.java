package com.training.practice.service;

import com.training.practice.dto.SubjectCreateDTO;
import com.training.practice.dto.UserCreateDTO;
import com.training.practice.dto.UserDTO;
import com.training.practice.dto.UserUpdateDTO;
import com.training.practice.entity.Subject;
import com.training.practice.entity.User;
import com.training.practice.repository.UserRepository;
import com.training.practice.mapper.UserMapper;
import com.training.practice.mapper.SubjectMapper;
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
    private final UserMapper userMapper;
    private final SubjectMapper subjectMapper;
    
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
        
        return userMapper.toDTO(savedUser);
    }
    
    @Transactional(readOnly = true)
    public Optional<UserDTO> getUserById(String id) {
        log.info("Fetching user by ID: {}", id);
        return userRepository.findById(id)
                .map(userMapper::toDTO);
    }
    
    @Transactional(readOnly = true)
    public List<UserDTO> getAllUsers() {
        log.info("Fetching all users");
        return userRepository.findAll().stream()
                .map(userMapper::toDTO)
                .collect(Collectors.toList());
    }
    
    @Transactional(readOnly = true)
    public Page<UserDTO> getUsersWithPagination(Pageable pageable) {
        log.info("Fetching users with pagination: {}", pageable);
        return userRepository.findAll(pageable)
                .map(userMapper::toDTO);
    }
    
    @Transactional(readOnly = true)
    public List<UserDTO> getUsersByDepartment(String department) {
        log.info("Fetching users by department: {}", department);
        return userRepository.findByDepartment(department).stream()
                .map(userMapper::toDTO)
                .collect(Collectors.toList());
    }
    
    @Transactional(readOnly = true)
    public List<UserDTO> getUsersByStatus(User.UserStatus status) {
        log.info("Fetching users by status: {}", status);
        return userRepository.findByStatus(status).stream()
                .map(userMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<UserDTO> getUsersByDepartmentAndStatus(String department, User.UserStatus status) {
        log.info("Fetching users by department: {} and status: {}", department, status);
        return userRepository.findByDepartmentAndStatus(department, status).stream()
                .map(userMapper::toDTO)
                .collect(Collectors.toList());
    }
    
    @Transactional(readOnly = true)
    public List<UserDTO> searchUsersByName(String name) {
        log.info("Searching users by name: {}", name);
        return userRepository.findByNameContainingIgnoreCase(name).stream()
                .map(userMapper::toDTO)
                .collect(Collectors.toList());
    }
    
    public UserDTO updateUser(String id, UserUpdateDTO request) {
        log.info("Updating user with ID: {}", id);
        
        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found with ID: " + id));
        
        // Check for email uniqueness if email is being updated
        if (request.getEmail() != null) {
            userRepository.findByEmail(request.getEmail())
                    .ifPresent(existingUser -> {
                        if (!existingUser.getId().equals(id)) {
                            throw new RuntimeException("Email already exists: " + request.getEmail());
                        }
                    });
        }
        
        // Use MapStruct to update the entity
        userMapper.updateEntityFromUpdateDTO(request, user);
        
        User updatedUser = userRepository.save(user);
        log.info("User updated successfully with ID: {}", updatedUser.getId());
        
        return userMapper.toDTO(updatedUser);
    }

    public UserDTO addNewSubjectToUser(String userId, SubjectCreateDTO request) {
        log.info("Adding new subject to user with ID: {}", userId);
        
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found with ID: " + userId));
        
        // Use SubjectMapper to create Subject from CreateDTO
        Subject subject = subjectMapper.createDTOToEntity(request);
        subject.setUser(user);
        user.getSubjects().add(subject);
        
        User updatedUser = userRepository.save(user);
        log.info("Subject added successfully to user with ID: {}", updatedUser.getId());
        
        return userMapper.toDTO(updatedUser);
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
