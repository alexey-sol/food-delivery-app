package com.github.alexeysol.app.service;

import com.github.alexeysol.app.model.entity.User;
import com.github.alexeysol.app.model.entity.Role;
import com.github.alexeysol.app.repository.UserRepository;
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
            throw new UsernameNotFoundException("User not found"); // TODO
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
            .id(1L) // TODO really need to set id explicitly?
            .name("ROLE_USER")
            .build();
        user.setRoles(Collections.singleton(role));
//        user.setRoles(Collections.singleton(new UserRole(1L, "ROLE_USER"))); // TODO do this in mapper
        user.setPassword(passwordEncoder.encode(user.getPassword()));

        return userRepository.save(user);
    }

    public void deleteUserById(long id) {
        userRepository.deleteById(id);
    }
}
