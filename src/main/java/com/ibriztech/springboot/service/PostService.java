package com.ibriztech.springboot.service;

import com.ibriztech.springboot.dto.PostDto;

import java.util.List;

public interface PostService {
    List<PostDto> findAllPosts();
    void createPost(PostDto postDto);

}
