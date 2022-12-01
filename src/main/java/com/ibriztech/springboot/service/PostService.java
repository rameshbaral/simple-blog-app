package com.ibriztech.springboot.service;

import com.ibriztech.springboot.dto.PostDto;
import com.ibriztech.springboot.entity.Post;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

public interface PostService {
    List<PostDto> findAllPosts();

    List<PostDto> findPostByUser();

    void createPost(PostDto postDto);

    PostDto findPostById(Long postId);

    void updatePost(PostDto postDto);

    void deletePost(Long postId);

    PostDto findPostByUrl(String postUrl);

    List<PostDto> searchPosts(String query);

    List<PostDto> generateExcel() throws IOException;

    List<PostDto> readFromExcel() throws IOException;
}
