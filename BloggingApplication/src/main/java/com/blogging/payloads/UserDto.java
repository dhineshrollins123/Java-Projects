package com.blogging.payloads;

import com.blogging.entities.Comment;
import com.blogging.entities.Role;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.Column;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class UserDto {

    private int userId;

    @NotEmpty
    @Size(min = 5,message = "Username must be min of 5 characters ")
    private String name;

    @NotEmpty(message = "Email should not be empty")
    @Email(message = "Email must be valid")
    private String email;

    @NotEmpty(message = "Password should not be empty")
    @Size(min = 8,message = "Password must be minimum 8 characters")
    private String password;

    @NotEmpty
    private String about;

    private List<CommentDto> userComments = new ArrayList<>();
    private Set<RoleDto> roles = new HashSet<>();

    @JsonIgnore
    public String getPassword() {
        return password;
    }

    @JsonProperty
    public void setPassword(String password) {
        this.password = password;
    }
}
