package com.blogging.controllers;

import com.blogging.payloads.ApiResponse;
import com.blogging.payloads.CommentDto;
import com.blogging.services.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api")
public class CommentController {

    @Autowired
    private CommentService commentService;

    @PostMapping("/posts/{postId}/comments")
    public ResponseEntity<CommentDto> createComment(@Valid @RequestBody CommentDto commentDto,
                                                    @PathVariable Integer postId){
        CommentDto comment = commentService.createComment(commentDto,postId);
        return new ResponseEntity<>(comment, HttpStatus.CREATED);
    }

    @DeleteMapping("/comments")
    public ResponseEntity<ApiResponse> deleteParticularComment(@RequestParam Integer commentId){
        commentService.deleteComment(commentId);
        return new ResponseEntity<>(new ApiResponse("Comment deleted successfully",true),
                HttpStatus.OK);
    }

    /*  @PutMapping
    public ResponseEntity<UserDto> updateUser(@Valid @RequestBody UserDto userDto, @RequestParam Integer userId){
        UserDto user = commentService.updateUser(userDto,userId);
        return new ResponseEntity<>(user, HttpStatus.OK);
    }*/

   /* @GetMapping
    public ResponseEntity<UserDto> getParticularUser(@RequestParam Integer userId){
        UserDto user = commentService.getUserById(userId);
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    @GetMapping(value = "/list")
    public ResponseEntity<List<UserDto>> retrieveAllUsers(){
        List<UserDto> users = commentService.getAllUsers();
        return new ResponseEntity<>(users, HttpStatus.OK);
    }*/

}

