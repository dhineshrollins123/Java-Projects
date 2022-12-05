package com.blogging.payloads;

import com.blogging.entities.Comment;
import com.blogging.entities.Role;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

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
public class UserDto {

    private int userId;

    @NotEmpty
    private String name;

    @Email(message = "EMail must be valid")
    private String email;

    @NotEmpty
    @Size(min = 8,message = "password must be minimum 8 characters")
    private String password;

    @NotEmpty
    private String about;

    private List<CommentDto> userComments = new ArrayList<>();
    private Set<RoleDto> roles = new HashSet<>();

}
