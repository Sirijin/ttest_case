package com.example.demo.dto;

import com.example.demo.model.Role;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
public class UserDTO {
    @NotNull
    private String username;
    @NotNull
    private String password;
    private Set<Role> roles;
}
