package com.github.alexeysol.gateway.service;

import com.github.alexeysol.common.feature.locality.model.dto.LocalityDto;
import com.github.alexeysol.common.shared.constant.ResourceConstant;
import com.github.alexeysol.gateway.config.GatewayConfig;
import lombok.RequiredArgsConstructor;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class LocalityService {
    private final GatewayConfig config;

    public List<LocalityDto> getLocalities() {
        return config.appWebClient()
            .get()
            .uri(builder -> builder.pathSegment(ResourceConstant.LOCALITY).build())
            .retrieve()
            .bodyToMono(new ParameterizedTypeReference<List<LocalityDto>>() {})
            .block();
    }
}
