package com.example.demo.service;

import com.example.demo.dto.UserDTO;
import com.example.demo.model.Role;
import com.example.demo.model.User;
import com.example.demo.repository.UserRepo;
import com.example.demo.util.UserAlreadyExistsException;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class RegistrationService {
    private final UserRepo userRepo;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public void register(@Valid UserDTO userDTO) throws UserAlreadyExistsException {
        if (userExists(userDTO.getUsername())) {
            throw new UserAlreadyExistsException("User with this username already exists");
        }

        User user = new User();
        user.setUsername(userDTO.getUsername());
        user.setPassword(passwordEncoder.encode(userDTO.getPassword()));

        Role role = new Role();
        role.setRole("ROLE_USER");
        user.getRoles().add(role);

        userRepo.save(user);
    }

    private boolean userExists(String username) {
        return userRepo.findByUsername(username).isPresent();
    }
}
