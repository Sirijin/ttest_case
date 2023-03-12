package com.example.demo.service;

import com.example.demo.dto.UserDTO;
import com.example.demo.model.User;
import com.example.demo.repository.UserRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class UserService {
    private final UserRepo userRepo;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public UserDTO update(long id, UserDTO userDTO) {
        Optional<User> optionalUser = userRepo.findById(id);
        User user = new User();
        if (optionalUser.isPresent()) {
            user.setUsername(userDTO.getUsername());
            user.setRoles(userDTO.getRoles());
            user.setPassword(passwordEncoder.encode(userDTO.getPassword()));
        }
        return convertToDTO(user);
    }

    private UserDTO convertToDTO(User user) {
        UserDTO userDTO = new UserDTO();
        userDTO.setUsername(user.getUsername());
        userDTO.setPassword(passwordEncoder.encode(user.getPassword()));
        userDTO.setRoles(userDTO.getRoles());
        return userDTO;
    }

    @Transactional
    public void delete(long id) {
        Optional<User> optionalUser = userRepo.findById(id);
        if (optionalUser.isPresent() && userRepo.findById(id).get().getRoles().stream().noneMatch(r -> r.getRole().equals("ROLE_SUPERUSER"))) {
            userRepo.deleteById(id);
        }
    }
}
