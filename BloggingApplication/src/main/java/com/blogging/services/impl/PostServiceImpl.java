package com.blogging.services.impl;

import com.blogging.entities.Category;
import com.blogging.entities.Comment;
import com.blogging.entities.Post;
import com.blogging.entities.User;
import com.blogging.exceptions.ResourceNotFoundException;
import com.blogging.payloads.CommentDto;
import com.blogging.payloads.PostDto;
import com.blogging.payloads.PostPagingResponse;
import com.blogging.payloads.UserDto;
import com.blogging.repositories.CategoryRepository;
import com.blogging.repositories.PostRepository;
import com.blogging.repositories.UserRepository;
import com.blogging.services.PostService;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class PostServiceImpl implements PostService {

    private final PostRepository postRepository;
    private final UserRepository userRepository;
    private final CategoryRepository categoryRepository;

    private ModelMapper modelMapper;

    @Override
    public PostDto createPost(PostDto postDto, Integer userId, Integer categoryId) {

        User user = userRepository.findById(userId).orElseThrow(()
                -> new ResourceNotFoundException("User", "user id", userId));

        Category category = categoryRepository.findById(categoryId).orElseThrow(()
                -> new ResourceNotFoundException("Category", "category id", categoryId));

        Post post = modelMapper.map(postDto, Post.class);

        post.setAddedDate(LocalDateTime.now());
        post.setUser(user);
        post.setCategory(category);

        Post savedPost = postRepository.save(post);

        return modelMapper.map(savedPost, PostDto.class);
    }

    @Override
    public PostDto updatePost(PostDto postDto, Integer postId) {

        Post post = postRepository.findById(postId).orElseThrow(() ->
                new ResourceNotFoundException("Post", "post id", postId));

        post.setContent(postDto.getContent());
        post.setTitle(postDto.getTitle());
        post.setAddedDate(LocalDateTime.now());
        post.setImageName(postDto.getImageName());

        Category category = categoryRepository.findById(postDto.getCategory().getCategoryId()).get();
        post.setCategory(category);

        Post updatedPost = postRepository.save(post);
        return modelMapper.map(updatedPost, PostDto.class);
    }

    @Override
    public PostDto getPostById(Integer postId) {
        Post post = postRepository.findById(postId).orElseThrow(() ->
                new ResourceNotFoundException("Post", "post id", postId));

        PostDto postDto = modelMapper.map(post, PostDto.class);

        postDto.getPostComments().clear();

        for (Comment comment : post.getPostComments()) {
            CommentDto commentDto = modelMapper.map(comment, CommentDto.class);
            UserDto userDto = modelMapper.map(comment.getUser(), UserDto.class);
            commentDto.setUserDto(userDto);

            postDto.getPostComments().add(commentDto);
        }

        for (CommentDto comment : postDto.getPostComments()) {
            System.out.println(" post comment : " + comment.toString());
        }

        return postDto;
    }

    @Override
    public List<PostDto> getAllPosts() {
        return postRepository.findAll().stream()
                .map(post -> modelMapper.map(post, PostDto.class))
                .collect(Collectors.toList());
    }

    @Override
    public void deletePost(Integer postId) {
        Post post = postRepository.findById(postId).orElseThrow(() ->
                new ResourceNotFoundException("Post", "post id", postId));
        postRepository.delete(post);
    }

    @Override
    public List<PostDto> getPostsByCategory(Integer categoryId) {

        Category category = categoryRepository.findById(categoryId).orElseThrow(() -> new ResourceNotFoundException("Category", "category id ", categoryId));
        List<Post> postsByCategory = postRepository.findAllByCategory(category);

        return postsByCategory.stream()
                .map(post -> modelMapper.map(post, PostDto.class))
                .collect(Collectors.toList());
    }

    @Override
    public List<PostDto> getPostsByUser(Integer userId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User", "user id ", userId));
        List<Post> postsByUser = postRepository.findAllByUser(user);

        return postsByUser.stream()
                .map(post -> modelMapper.map(post, PostDto.class))
                .collect(Collectors.toList());
    }

    @Override
    public List<PostDto> searchPosts(String keyword) {
        return postRepository.searchAllPostByTitle("%" + keyword + "%")
                .stream()
                .map(post -> modelMapper.map(post, PostDto.class))
                .collect(Collectors.toList());
    }

    @Override
    public PostPagingResponse getAllPostsWithPagingAndSorting(Integer pageNumber, Integer pageSize, String sortBy) {
        Pageable pageRequest = PageRequest.of(pageNumber, pageSize, Sort.by(sortBy).descending()); //=> For Descending order
        // Pageable pageRequest = PageRequest.of(pageNumber, pageSize, Sort.by(sortBy)); // => For Ascending order
        Page<Post> postPage = postRepository.findAll(pageRequest);

        List<PostDto> postDtoList = postPage.getContent()
                .stream()
                .map(post -> modelMapper.map(post, PostDto.class))
                .collect(Collectors.toList());

        PostPagingResponse response = new PostPagingResponse();
        response.setContent(postDtoList);
        response.setPageNumber(postPage.getNumber());
        response.setTotalPages(postPage.getTotalPages());
        response.setPageSize(postPage.getSize());
        response.setTotalElements(postPage.getNumberOfElements());
        response.setLastPage(postPage.isLast());

        return response;
    }
}
