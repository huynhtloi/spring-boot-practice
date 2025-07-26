package com.training.practice.repository;

import com.training.practice.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, String> {
    
    Optional<User> findByEmail(String email);
    List<User> findByDepartment(String department);
    List<User> findByStatus(User.UserStatus status);
    List<User> findByNameContainingIgnoreCase(String name);
    // @Query("SELECT u FROM User u WHERE u.department = :department AND u.status = :status")
    // List<User> findByDepartmentAndStatus(@Param("department") String department, 
    //                                    @Param("status") User.UserStatus status);
    List<User> findByDepartmentAndStatus(String department, User.UserStatus status);
    Page<User> findByDepartment(String department, Pageable pageable);
    long countByStatus(User.UserStatus status);
    boolean existsByEmail(String email);
}
