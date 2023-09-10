package com.github.alexeysol.app.feature.user.service;

import com.github.alexeysol.app.feature.user.model.entity.User;
import com.github.alexeysol.app.feature.user.model.entity.Role;
import com.github.alexeysol.app.feature.user.repository.UserRepository;
import com.github.alexeysol.common.shared.model.UserRole;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService implements UserDetailsService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByUsername(username).orElseThrow(() -> {
            throw new UsernameNotFoundException("User not found");
        });
    }

    public Optional<User> findUserById(long id) {
        return userRepository.findById(id);
    }

    public Optional<User> findUserByPhone(String phone) {
        return userRepository.findByPhone(phone);
    }

    public User saveUser(User user) {
        var role = Role.builder()
            .id(2)
            .name(UserRole.CUSTOMER)
            .build();
        user.setRoles(Collections.singleton(role));
        user.setPassword(passwordEncoder.encode(user.getPassword()));

        return userRepository.save(user);
    }

    public boolean isValidPassword(String passwordToCheck, User user) {
        return passwordEncoder.matches(passwordToCheck, user.getPassword());
    }
}
