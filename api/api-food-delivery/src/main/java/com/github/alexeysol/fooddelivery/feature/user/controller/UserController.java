package com.github.alexeysol.fooddelivery.feature.user.controller;

import com.github.alexeysol.common.feature.user.model.dto.SignUpDto;
import com.github.alexeysol.common.feature.user.model.dto.UserDto;
import com.github.alexeysol.common.feature.locality.exception.LocalityNotFoundException;
import com.github.alexeysol.fooddelivery.feature.locality.service.LocalityService;
import com.github.alexeysol.common.feature.user.exception.UserNotFoundException;
import com.github.alexeysol.fooddelivery.feature.user.mapper.UserMapper;
import com.github.alexeysol.fooddelivery.feature.user.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.Objects;
import java.util.Optional;

@RestController
@RequestMapping(value = "/user", produces = "application/json")
@RequiredArgsConstructor
public class UserController {
    private final LocalityService localityService;
    private final UserService userService;

    private final UserMapper userMapper;

    @GetMapping("/{id}")
    public UserDto getUserById(@PathVariable long id) {
        var user = userService.findUserById(id).orElseThrow(() -> {
            throw new UserNotFoundException();
        });

        return userMapper.map(user);
    }

    @GetMapping
    public UserDto getUserByPhone(@RequestParam String phone, @RequestParam Optional<String> password) {
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
        var localityId = dto.getLocalityId();

        var locality = localityService.findLocalityById(localityId).orElseThrow(() -> {
            throw new LocalityNotFoundException();
        });

        var userToCreate = userMapper.map(dto, locality);
        userService.saveUser(userToCreate);
        return userMapper.map(userToCreate);
    }
}
