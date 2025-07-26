package com.training.practice.controller;

import com.training.practice.dto.HelloResponse;
import com.training.practice.service.GreetingManagerService;
import com.training.practice.service.GreetingService;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/di")
public class DependencyInjectionController {

    // Field injection with @Qualifier
    private final GreetingService casualService;
    private final GreetingService formalService;
    private final GreetingManagerService greetingManager;

    // Constructor with @Qualifier
    public DependencyInjectionController(
            @Qualifier("helloService") GreetingService casualService,
            @Qualifier("formalGreetingService") GreetingService formalService,
            GreetingManagerService greetingManager) {
        this.casualService = casualService;
        this.formalService = formalService;
        this.greetingManager = greetingManager;
    }

    @GetMapping("/casual")
    public HelloResponse casualGreeting(@RequestParam(value = "name", required = false) String name) {
        String message = casualService.generateGreeting(name);
        return new HelloResponse(message, System.currentTimeMillis());
    }

    @GetMapping("/formal")
    public HelloResponse formalGreeting(@RequestParam(value = "name", required = false) String name) {
        String message = formalService.generateGreeting(name);
        return new HelloResponse(message, System.currentTimeMillis());
    }

    @GetMapping("/managed")
    public HelloResponse managedGreeting(
            @RequestParam(value = "name", required = false) String name,
            @RequestParam(value = "type", defaultValue = "casual") String type) {
        String message = greetingManager.getGreetingByType(name, type);
        return new HelloResponse(message, System.currentTimeMillis());
    }

    @GetMapping("/welcome")
    public HelloResponse managedWelcome(
            @RequestParam(value = "name", required = false) String name,
            @RequestParam(value = "type", defaultValue = "casual") String type) {
        String message = greetingManager.getWelcomeByType(name, type);
        return new HelloResponse(message, System.currentTimeMillis());
    }

    @GetMapping("/staff")
    public HelloResponse managedStaff(
            @RequestParam(value = "name", required = false) String name,
            @RequestParam(value = "type", defaultValue = "casual") String type) {
        String message = greetingManager.getRandomStaffByType(name, type);
        return new HelloResponse(message, System.currentTimeMillis());
    }
}
