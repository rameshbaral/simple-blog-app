package com.ibriztech.springboot.service.impl;

import com.ibriztech.springboot.dto.PostDto;
import com.ibriztech.springboot.entity.Post;
import com.ibriztech.springboot.mapper.PostMappper;
import com.ibriztech.springboot.repository.PostRepository;
import com.ibriztech.springboot.service.PostService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PostServiceImpl implements PostService {

    private PostRepository postRepository;

    public PostServiceImpl(PostRepository postRepository) {
        this.postRepository = postRepository;
    }
    ///returns list of PostDto by Searching On Database
    @Override
    public List<PostDto> findAllPosts() {
        List<Post> posts =  postRepository.findAll();
        return posts.stream().map((PostMappper :: mapToPostDto))
                .collect(Collectors.toList());
    }

}