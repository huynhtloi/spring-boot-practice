package com.training.practice.service;

import com.training.practice.dto.GreetingRequest;

import lombok.Data;

import org.springframework.stereotype.Service;

@Service("helloService")
@Data
public class HelloService implements GreetingService {

    public String generateGreeting(String name) {
        if (name == null || name.trim().isEmpty()) {
            return "Hello guys!";
        }
        return "Hello " + name.trim() + "!";
    }

    public String generateWelcomeMessage(String name) {
        if (name == null || name.trim().isEmpty()) {
            return "Welcome to our Spring Boot API!";
        }
        return "Welcome " + name.trim() + " to our Spring Boot API!";
    }

    public String generateCustomGreeting(GreetingRequest request) {
        if (request == null || request.getName() == null) {
            return "Hello there!";
        }
        
        String greeting = request.isFormal() ? "Good day" : "Hi";
        String name = request.getName().trim();
        
        if ("vi".equalsIgnoreCase(request.getLanguage())) {
            greeting = request.isFormal() ? "Chào" : "Xin chào";
        }
        else if ("en".equalsIgnoreCase(request.getLanguage())) {
            greeting = request.isFormal() ? "Hi" : "Hello";
        }
        
        return greeting + " " + name + "!";
    }

    public String getRandomStaff(String name) {
        String[] staffs = {
            "Loi",
            "Tyler", 
            "Thuan",
            "Huy",
            "Nguyen"
        };
        
        String staff = staffs[(int) (Math.random() * staffs.length)];
        return staff + " says hello to " + (name != null ? name : "Guest") + "!";
    }
}
