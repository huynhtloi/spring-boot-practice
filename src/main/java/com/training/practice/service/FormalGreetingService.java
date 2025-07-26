package com.training.practice.service;

import com.training.practice.dto.GreetingRequest;

import org.springframework.stereotype.Service;

@Service("formalGreetingService")
public class FormalGreetingService implements GreetingService {

    @Override
    public String generateGreeting(String name) {
        if (name == null || name.trim().isEmpty()) {
            return "Good day, everyone!";
        }
        return "Good day, " + name.trim() + "!";
    }

    @Override
    public String generateWelcomeMessage(String name) {
        if (name == null || name.trim().isEmpty()) {
            return "We welcome you to our distinguished Spring Boot API!";
        }
        return "We welcome you, " + name.trim() + ", to our distinguished Spring Boot API!";
    }

    @Override
    public String generateCustomGreeting(GreetingRequest request) {
        
        if (request == null || request.getName() == null) {
            return "Greetings!";
        }
        
        String greeting = "Good day";
        String name = request.getName().trim();
        
        if ("vi".equalsIgnoreCase(request.getLanguage())) {
            greeting = "Kính chào";
        } else if ("en".equalsIgnoreCase(request.getLanguage())) {
            greeting = request.isFormal() ? "Good day" : "Good morning";
        }
        
        return greeting + ", " + name + "!";
    }

    @Override
    public String getRandomStaff(String name) {
        String[] staffs = {
            "Mr. Loi",
            "Mr. Tyler", 
            "Ms. Thuan",
            "Mr. Huy",
            "Mr. Nguyen"
        };
        
        String staff = staffs[(int) (Math.random() * staffs.length)];
        return staff + " cordially greets " + (name != null ? name : "our esteemed guest") + "!";
    }
}
