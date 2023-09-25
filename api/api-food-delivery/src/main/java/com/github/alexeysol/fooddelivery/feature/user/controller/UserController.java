package com.github.alexeysol.fooddelivery.feature.user.controller;

import com.github.alexeysol.common.shared.constant.ErrorMessageConstant;
import com.github.alexeysol.fooddelivery.feature.user.mapper.UserMapper;
import com.github.alexeysol.fooddelivery.feature.city.service.CityService;
import com.github.alexeysol.fooddelivery.feature.user.service.UserService;
import com.github.alexeysol.common.feature.user.model.dto.SignUpDto;
import com.github.alexeysol.common.feature.user.model.dto.UserDto;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.Objects;
import java.util.Optional;

@RestController
@RequestMapping(value = "/user", produces = "application/json")
@RequiredArgsConstructor
public class UserController {
    private final static String CITY = "City";
    private final static String USER = "User";

    private final CityService cityService;
    private final UserService userService;

    private final UserMapper userMapper;

    @GetMapping("/{id}")
    public UserDto getUserById(@PathVariable long id) {
        var user = userService.findUserById(id).orElseThrow(() -> {
            var message = String.format(ErrorMessageConstant.NOT_FOUND_BY_ID, USER, id);
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, message);
        });

        return userMapper.map(user);
    }

    @GetMapping
    public UserDto getUser(@RequestParam String phone, @RequestParam Optional<String> password) {
        var user = userService.findUserByPhone(phone).orElse(null);

        if (Objects.isNull(user) || (password.isPresent() && !userService.isValidPassword(password.get(), user))) {
            return null;
        }

        return userService.findUserByPhone(phone)
            .map(userMapper::map)
            .orElse(null);
    }

    @PostMapping
    public UserDto createUser(@RequestBody @Valid SignUpDto dto) {
        var cityId = dto.getAddress().getCityId();

        var city = cityService.findCityById(cityId).orElseThrow(() -> {
            var message = String.format(ErrorMessageConstant.NOT_FOUND_BY_ID, CITY, cityId);
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, message);
        });

        var userToCreate = userMapper.map(dto, city);
        userService.saveUser(userToCreate);
        return userMapper.map(userToCreate);
    }
}
