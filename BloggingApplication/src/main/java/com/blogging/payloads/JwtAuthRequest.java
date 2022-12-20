package com.blogging.payloads;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class JwtAuthRequest {
    @NotEmpty(message = "username must not be empty")
    private String userName;
    @NotEmpty(message = "password must not be empty")
    private String password;
}
