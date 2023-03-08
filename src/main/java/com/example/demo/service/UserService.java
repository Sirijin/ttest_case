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
        Optional<User> user = userRepo.findById(id);
        if (user.isPresent()) {
            user.get().setUsername(userDTO.getUsername());
            user.get().setRoles(userDTO.getRoles());
            user.get().setPassword(passwordEncoder.encode(userDTO.getPassword()));


        }
        return null;
    }

    private UserDTO convertToDTO(User user) {
        UserDTO userDTO = new UserDTO();
        userDTO.setUsername(user.getUsername());
//        userDTO.setPassword();
        userDTO.setRoles(userDTO.getRoles());
        return userDTO;
    }
}
