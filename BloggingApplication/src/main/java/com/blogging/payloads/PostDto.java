package com.blogging.payloads;

import com.blogging.entities.Comment;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class PostDto {

    private Integer postId;
    @NotEmpty(message = "title field is mandatory")
    private String title;
    @NotEmpty(message = "content field is mandatory")
    private String content;
    private String imageName;
    private LocalDateTime addedDate;
    private CategoryDto category;
    private UserDto user;
    private List<CommentDto> postComments = new ArrayList<>();
}

