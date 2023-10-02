package com.github.alexeysol.gateway.service;

import com.github.alexeysol.common.feature.city.model.dto.CityDto;
import com.github.alexeysol.common.shared.constant.ResourceConstant;
import com.github.alexeysol.gateway.config.GatewayConfig;
import lombok.RequiredArgsConstructor;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CityService {
    private final GatewayConfig config;

    public List<CityDto> getCities() {
        return config.appWebClient()
            .get()
            .uri(builder -> builder.pathSegment(ResourceConstant.CITY).build())
            .retrieve()
            .bodyToMono(new ParameterizedTypeReference<List<CityDto>>() {})
            .block();
    }
}
