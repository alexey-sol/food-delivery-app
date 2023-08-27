package com.github.alexeysol.gateway.controller;

import com.github.alexeysol.common.model.dto.SignInDto;
import com.github.alexeysol.common.model.dto.SignUpDto;
import com.github.alexeysol.common.model.dto.UserDto;
import com.github.alexeysol.gateway.config.GatewayConfig;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.reactive.function.BodyInserters;

@RestController
@RequestMapping(value = "/user", produces = "application/json")
@RequiredArgsConstructor
public class UserController {
    private final static String USER_RESOURCE = "user";

    private final GatewayConfig config;

    @GetMapping("/{id}")
    public UserDto getUserById(@PathVariable long id) {
        return config.appWebClient()
            .get()
            .uri(builder -> builder.pathSegment(USER_RESOURCE, String.valueOf(id)).build())
            .retrieve()
            .bodyToMono(UserDto.class)
            .block();
    }

    // TODO
//    @PostMapping
//    public UserDto signUp(@RequestBody SignUpDto dto) {
//        return config.appWebClient()
//            .post()
//            .uri(builder -> builder.pathSegment(USER_RESOURCE).build())
//            .body(BodyInserters.fromValue(dto))
//            .retrieve()
//            .bodyToMono(UserDto.class)
//            .block();
//    }
//
//    @PutMapping
//    public UserDto signUp(@RequestBody SignInDto dto) {
//        return config.appWebClient()
//            .post()
//            .uri(builder -> builder.pathSegment(USER_RESOURCE).build())
//            .body(BodyInserters.fromValue(dto))
//            .retrieve()
//            .bodyToMono(UserDto.class)
//            .block();
//    }
}
