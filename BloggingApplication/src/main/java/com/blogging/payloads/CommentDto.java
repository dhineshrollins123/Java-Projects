package com.blogging.payloads;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;

@Getter
@Setter
@NoArgsConstructor
public class CommentDto {

    private int commentId;

    @NotEmpty
    private String content;

   /* private PostDto post;

    private UserDto user;*/
}
