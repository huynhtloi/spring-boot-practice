package com.training.practice.service;

import com.training.practice.interfaces.GreetingService;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service
public class GreetingManagerService {

    private final GreetingService casualGreetingService;
    private final GreetingService formalGreetingService;

    public GreetingManagerService(
            @Qualifier("helloService") GreetingService casualGreetingService,
            @Qualifier("formalGreetingService") GreetingService formalGreetingService) {
        this.casualGreetingService = casualGreetingService;
        this.formalGreetingService = formalGreetingService;
    }

    public String getGreetingByType(String name, String type) {
        
        if ("formal".equalsIgnoreCase(type)) {
            return formalGreetingService.generateGreeting(name);
        } else {
            return casualGreetingService.generateGreeting(name);
        }
    }

    public String getWelcomeByType(String name, String type) {
        
        if ("formal".equalsIgnoreCase(type)) {
            return formalGreetingService.generateWelcomeMessage(name);
        } else {
            return casualGreetingService.generateWelcomeMessage(name);
        }
    }

    public String getRandomStaffByType(String name, String type) {
        
        if ("formal".equalsIgnoreCase(type)) {
            return formalGreetingService.getRandomStaff(name);
        } else {
            return casualGreetingService.getRandomStaff(name);
        }
    }
}
