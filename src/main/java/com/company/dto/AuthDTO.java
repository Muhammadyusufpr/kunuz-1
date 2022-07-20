package com.company.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.*;

@Setter
@Getter
@ToString
public class AuthDTO {
    @NotNull(message = "Email required")
    @Email(message = "Email required")
    private String email;

    @NotBlank(message = "Password required")
    @Size(min = 4, max = 15, message = "About me must be between 4 and 15 characters")
    private String password;
    //    @NotEmpty
//    @NotNull(message = "password null ku mazgi.")
}
