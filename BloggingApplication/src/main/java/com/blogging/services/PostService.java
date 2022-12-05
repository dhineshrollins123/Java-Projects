package com.blogging.services;

import com.blogging.payloads.PostDto;
import com.blogging.payloads.PostPagingResponse;

import java.util.List;

public interface PostService {
    PostDto createPost(PostDto user,Integer userId,Integer categoryId);
    PostDto updatePost(PostDto user,Integer postId);
    PostDto getPostById(Integer postId);
    List<PostDto> getAllPosts();
    void deletePost(Integer postId);
    List<PostDto> getPostsByCategory(Integer categoryId);
    List<PostDto> getPostsByUser(Integer userId);
    List<PostDto> searchPosts(String keyword);
    PostPagingResponse getAllPostsWithPagingAndSorting(Integer pageNumber, Integer pageSize, String sortBy);

}
