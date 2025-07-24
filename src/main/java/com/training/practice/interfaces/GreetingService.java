package com.training.practice.interfaces;

import com.training.practice.dto.GreetingRequest;

public interface GreetingService {
    String generateGreeting(String name);
    String generateWelcomeMessage(String name);
    String generateCustomGreeting(GreetingRequest request);
    String getRandomStaff(String name);
}
