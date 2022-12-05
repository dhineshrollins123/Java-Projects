package com.blogging.payloads;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;

@Getter
@Setter
public class RoleDto {
    private int roleId;
    @NotEmpty
    private String roleName;
}
