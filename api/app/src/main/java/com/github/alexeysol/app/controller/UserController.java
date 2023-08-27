package com.github.alexeysol.app.controller;

import com.github.alexeysol.app.constant.ErrorMessageConstant;
import com.github.alexeysol.app.mapper.UserMapper;
import com.github.alexeysol.app.model.entity.User;
import com.github.alexeysol.app.service.CityService;
import com.github.alexeysol.app.service.UserService;
import com.github.alexeysol.common.model.dto.SignInDto;
import com.github.alexeysol.common.model.dto.SignUpDto;
import com.github.alexeysol.common.model.dto.UserDto;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

@RestController
@RequestMapping(value = "/user", produces = "application/json")
@RequiredArgsConstructor
public class UserController {
    private final static String CITY_RESOURCE = "City";
    private final static String USER_RESOURCE = "User";

    private final CityService cityService;
    private final UserService userService;

    private final UserMapper userMapper = UserMapper.INSTANCE;

    @GetMapping("/{id}")
    public UserDto getUserById(@PathVariable long id) {
        var user = userService.findUserById(id).orElseThrow(() -> {
            var message = String.format(ErrorMessageConstant.NOT_FOUND_BY_ID, USER_RESOURCE, id);
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, message);
        });

        return userMapper.map(user);
    }

    @GetMapping
    public UserDto getUser(@RequestParam String phone) {
        return userService.findUserByPhone(phone)
            .map(userMapper::map)
            .orElse(null);
    }

    // TODO move to auth controller? sign up and sign in
    @PostMapping
    public UserDto createUser(@RequestBody @Valid SignUpDto dto) { // TODO create CreateUserDto? like SignUpDto but without passwordConfirm
        var cityId = dto.getAddress().getCityId();

        var city = cityService.findCityById(cityId).orElseThrow(() -> {
            var message = String.format(ErrorMessageConstant.NOT_FOUND_BY_ID, CITY_RESOURCE, cityId);
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, message);
        });

        var userToCreate = userMapper.map(dto, city);
        userService.saveUser(userToCreate);

        return userMapper.map(userToCreate);
    }
}
