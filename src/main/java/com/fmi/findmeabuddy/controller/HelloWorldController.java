package com.fmi.findmeabuddy.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
public class HelloWorldController {
    @GetMapping("/hello")
    Mono<String> get() {
        return Mono.just("Hello world!");
    }
}
