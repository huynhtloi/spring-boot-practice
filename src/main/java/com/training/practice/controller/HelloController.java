package com.training.practice.controller;

import com.training.practice.dto.GreetingRequest;
import com.training.practice.dto.HelloResponse;
import com.training.practice.service.GreetingService;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

// @RequiredArgsConstructor
@RestController
@RequestMapping(value = "/api")
public class HelloController {

    // Field injection with Autowired and @Qualifier
    // @Autowired
    // @Qualifier("helloService")
    // private GreetingService greetingService;

    private final GreetingService greetingService;

    // Constructor with @Qualifier
    public HelloController(@Qualifier("helloService") GreetingService greetingService) {
        this.greetingService = greetingService;
    }

    @GetMapping("/hello")
    public String hello() {
        return greetingService.generateGreeting(null);
    }

    @GetMapping("/hello/{name}")
    public String helloWithName(@PathVariable String name) {
        return greetingService.generateGreeting(name);
    }

    @GetMapping("/greeting")
    public String greeting(@RequestParam(value = "name", defaultValue = "World") String name) {
        return greetingService.generateGreeting(name);
    }

    @GetMapping("/welcome")
    public HelloResponse welcome(@RequestParam(value = "name", defaultValue = "Guest") String name) {
        String message = greetingService.generateWelcomeMessage(name);
        return new HelloResponse(message, System.currentTimeMillis());
    }

    @GetMapping("/random")
    public HelloResponse randomGreeting(@RequestParam(value = "name", required = false) String name) {
        String message = greetingService.getRandomStaff(name);
        return new HelloResponse(message, System.currentTimeMillis());
    }

    @PostMapping("/custom")
    public HelloResponse customGreeting(@RequestBody GreetingRequest request) {
        String message = greetingService.generateCustomGreeting(request);
        return new HelloResponse(message, System.currentTimeMillis());
    }
}
