package com.blogging.controllers;

import com.blogging.payloads.ApiResponse;
import com.blogging.payloads.PostDto;
import com.blogging.payloads.PostPagingResponse;
import com.blogging.services.FileService;
import com.blogging.services.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api")
public class PostController {

    @Value("${project.image}")
    private String path;

    @Autowired
    private PostService postService;

    @Autowired
    private FileService fileService;

    @PostMapping("/user/{userId}/category/{categoryId}/posts")
    public ResponseEntity<PostDto> createPost(@Valid @RequestBody PostDto postDto,
                    @PathVariable("userId") Integer userId,@PathVariable("categoryId") Integer catId){
        PostDto post = postService.createPost(postDto,userId,catId);
        return new ResponseEntity<>(post, HttpStatus.CREATED);
    }

    @PutMapping("/posts")
    public ResponseEntity<PostDto> updatePost(@Valid @RequestBody PostDto post, @RequestParam Integer postId){
        PostDto postDto = postService.updatePost(post,postId);
        return new ResponseEntity<>(postDto, HttpStatus.OK);
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @DeleteMapping("/posts")
    public ResponseEntity<ApiResponse> deleteParticularPost(@RequestParam Integer postId){
        postService.deletePost(postId);
        return new ResponseEntity<>(new ApiResponse("Post deleted successfully",true),HttpStatus.OK);
    }

    @GetMapping("/posts")
    public ResponseEntity<PostDto> getParticularPost(@RequestParam Integer postId){
        PostDto post = postService.getPostById(postId);
        return new ResponseEntity<>(post, HttpStatus.OK);
    }

    @GetMapping("/posts/list")
    public ResponseEntity<List<PostDto>> retrieveAllPosts(){
        List<PostDto> posts = postService.getAllPosts();
        return new ResponseEntity<>(posts, HttpStatus.OK);
    }

    @GetMapping("/posts/page")
    public ResponseEntity<PostPagingResponse> retrieveAllPostsWithPageableAndSorting(
            @RequestParam(value = "pageNumber",defaultValue = "0",required = false) Integer pageNumber,
            @RequestParam(value = "pageSize",defaultValue = "10",required = false) Integer pageSize,
            @RequestParam(value = "sortBy",defaultValue = "postId",required = false) String sortBy){

        PostPagingResponse response = postService.getAllPostsWithPagingAndSorting(pageNumber,pageSize,sortBy);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/category/{categoryId}/posts")
    public ResponseEntity<List<PostDto>> retrieveAllPostsByCategory(@PathVariable Integer categoryId){
        List<PostDto> users = postService.getPostsByCategory(categoryId);
        return new ResponseEntity<>(users, HttpStatus.OK);
    }

    @GetMapping("/user/{userId}/posts")
    public ResponseEntity<List<PostDto>> retrieveAllPostsByUser(@PathVariable Integer userId){
        List<PostDto> users = postService.getPostsByUser(userId);
        return new ResponseEntity<>(users, HttpStatus.OK);
    }

    @GetMapping("/posts/search/{title}")
    public ResponseEntity<List<PostDto>> searchPostsByTitle(@PathVariable("title") String title){

        List<PostDto> response = postService.searchPosts(title);
        return new ResponseEntity<>(response, HttpStatus.OK);

    }
    //post image upload
    @PostMapping("/posts/image/upload/{postId}")
    public ResponseEntity<PostDto> uploadImage(@RequestParam("image")MultipartFile image,
                                               @PathVariable("postId") Integer postId) throws IOException {

        PostDto post = postService.getPostById(postId);
        String fileName = fileService.uploadImage(path, image);
        post.setImageName(fileName);
        PostDto updatedPost = postService.updatePost(post, postId);
        return new ResponseEntity<>(updatedPost,HttpStatus.OK);
    }

}
