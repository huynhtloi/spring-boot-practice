package com.training.practice.client;

import java.util.List;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.service.annotation.DeleteExchange;
import org.springframework.web.service.annotation.GetExchange;
import org.springframework.web.service.annotation.HttpExchange;
import org.springframework.web.service.annotation.PatchExchange;
import org.springframework.web.service.annotation.PostExchange;
import org.springframework.web.service.annotation.PutExchange;

import com.training.practice.dto.backend.PostmanRequest;
import com.training.practice.dto.external.PostmanClientResponse;

@HttpExchange
public interface PostmanClient {
    
    @GetExchange("/users")
    List<PostmanClientResponse> getAllUsers();

    @GetExchange("/users/{id}")
    PostmanClientResponse getUserById(@PathVariable Long id);
    
    @GetExchange("/users")
    List<PostmanClientResponse> getUsersByRole(@RequestParam String role);

    @PostExchange("/users")
    PostmanClientResponse createUser(@RequestBody PostmanRequest request);    

    @PutExchange("/users/{id}")
    PostmanClientResponse updateFullUser(@PathVariable Long id, @RequestBody PostmanRequest request);

    @PatchExchange("/users/{id}")
    PostmanClientResponse updatePartialUser(@PathVariable Long id, @RequestBody PostmanRequest request);

    @DeleteExchange("/users/{id}")
    void deleteUser(@PathVariable Long id);
    
    @GetExchange("/users/{id}/permissions")
    List<String> getUserPermissions(@PathVariable Long id);
    
    @PostExchange("/users/{id}/roles")
    PostmanClientResponse assignRole(@PathVariable Long id, @RequestParam String role);
}