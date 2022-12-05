package com.blogging.services.impl;

import com.blogging.entities.Comment;
import com.blogging.entities.Post;
import com.blogging.exceptions.ResourceNotFoundException;
import com.blogging.payloads.CommentDto;
import com.blogging.payloads.PostDto;
import com.blogging.payloads.UserDto;
import com.blogging.repositories.CommentRepository;
import com.blogging.repositories.PostRepository;
import com.blogging.repositories.UserRepository;
import com.blogging.services.CommentService;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
public class CommentServiceImpl implements CommentService {

    private final PostRepository postRepository;
    private final CommentRepository commentRepository;

    private ModelMapper modelMapper;

    @Override
    public CommentDto createComment(CommentDto commentDto,Integer postId) {

        Post post = postRepository.findById(postId).orElseThrow(() ->
                new ResourceNotFoundException("Post", "post id", postId));

        Comment comment = modelMapper.map(commentDto, Comment.class);
        comment.setPost(post);
        comment.setUser(post.getUser());
        Comment savedComment = commentRepository.save(comment);
        return modelMapper.map(savedComment, CommentDto.class);
    }

    @Override
    public void deleteComment(Integer commentId) {
        Comment comment = commentRepository.findById(commentId).orElseThrow(() ->
                new ResourceNotFoundException("Comment", "comment id", commentId));

        commentRepository.delete(comment);
    }
}
