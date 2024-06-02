package com.example.lesure.security.auth.model;

import com.example.lesure.domain.constants.Role;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RegisterRequest {

  @NotBlank(message = "Firstname must not be blank")
  private String firstname;

  @NotBlank(message = "Lastname must not be blank")
  private String lastname;

  @NotBlank(message = "Email must not be blank")
  @Email
  private String email;

  @NotBlank(message = "Password must not be blank")
  @Size(min = 6, message = "Password must be at least 6 characters long")
  private String password;

  @NotNull(message = "Role must not be null")
  private Role role;
}
