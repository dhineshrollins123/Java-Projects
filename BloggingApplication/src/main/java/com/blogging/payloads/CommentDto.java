package com.blogging.payloads;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.NotEmpty;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class CommentDto {

    private int commentId;

    @NotEmpty
    private String content;

   /* private PostDto post;

    private UserDto user;*/

    private UserDto userDto;

}
