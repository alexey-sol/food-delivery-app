package com.github.alexeysol.gateway.service;

import com.github.alexeysol.common.shared.constant.ResourceConstant;
import com.github.alexeysol.common.feature.user.model.dto.SignUpDto;
import com.github.alexeysol.common.feature.user.model.dto.UserDto;
import com.github.alexeysol.gateway.config.GatewayConfig;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.BodyInserters;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {
    private final GatewayConfig config;

    public UserDto getUserById(long id) {
        return config.appWebClient()
            .get()
            .uri(builder -> builder.pathSegment(ResourceConstant.USER, String.valueOf(id)).build())
            .retrieve()
            .bodyToMono(UserDto.class)
            .block();
    }

    public UserDto getUser(String phone) {
        return getUser(phone, null);
    }

    public UserDto getUser(String phone, String password) {
        return config.appWebClient()
            .get()
            .uri(builder -> builder.pathSegment(ResourceConstant.USER)
                .queryParam("phone", phone)
                .queryParamIfPresent("password", Optional.ofNullable(password))
                .build())
            .retrieve()
            .bodyToMono(UserDto.class)
            .block();
    }

    public UserDto createUser(SignUpDto dto) {
        return config.appWebClient()
            .post()
            .uri(builder -> builder.pathSegment(ResourceConstant.USER).build())
            .body(BodyInserters.fromValue(dto))
            .retrieve()
            .bodyToMono(UserDto.class)
            .block();
    }
}
