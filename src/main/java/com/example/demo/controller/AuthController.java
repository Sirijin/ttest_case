package com.example.demo.controller;

import com.example.demo.dto.AuthenticationDTO;
import com.example.demo.dto.UserDTO;
import com.example.demo.security.JWTUtil;
import com.example.demo.service.RegistrationService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final RegistrationService registrationService;
    private final JWTUtil jwtUtil;
//    private final AuthenticationManager authenticationManager;
    private final AuthenticationProvider authenticationProvider;

    @PostMapping("/registration")
    public Map<String, String> performRegistration(@RequestBody @Valid UserDTO userDTO, BindingResult bindingResult) {
        bindingHandle(bindingResult);
        registrationService.register(userDTO);
        String token = generateToken(userDTO.getUsername());
        return Map.of("jwt-token", token);
    }

    @PostMapping("/login")
    public Map<String, String> performLogin(@RequestBody @Valid AuthenticationDTO authenticationDTO, BindingResult bindingResult) {
        bindingHandle(bindingResult);
        try {
            UsernamePasswordAuthenticationToken authInputToken = new UsernamePasswordAuthenticationToken(
                    authenticationDTO.getUsername(), authenticationDTO.getPassword()
            );
//            authenticationManager.authenticate(authInputToken);
            SecurityContextHolder.getContext().setAuthentication(authenticationProvider.authenticate(authInputToken));
            String token = generateToken(authenticationDTO.getUsername());
            return Map.of("jwt-token", token);
        } catch (BadCredentialsException e) {
            return Map.of("message", "Incorrect credentials");
        } catch (Exception e) {
            return Map.of("message", "An error occurred while logging in");
        }
    }

    private String generateToken(String username) {
        return jwtUtil.generateToken(username);
    }

    private void bindingHandle(BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            throw new IllegalArgumentException(bindingResult.getFieldErrors().toString());
        }
    }
}


