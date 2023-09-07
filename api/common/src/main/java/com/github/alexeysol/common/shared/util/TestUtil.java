package com.github.alexeysol.common.shared.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import lombok.experimental.UtilityClass;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

@UtilityClass
public class TestUtil {
    private static final ObjectMapper objectMapper = new ObjectMapper();

    @SneakyThrows
    public MockHttpServletRequestBuilder mockPostRequest(String url) {
        return mockPostRequest(url, null);
    }

    @SneakyThrows
    public <T> MockHttpServletRequestBuilder mockPostRequest(String url, T value) {
        return MockMvcRequestBuilders.post(url)
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(value));
    }

    @SneakyThrows
    public <T> MockHttpServletRequestBuilder mockPatchRequest(String url, T value) {
        return MockMvcRequestBuilders.patch(url)
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(value));
    }
}
