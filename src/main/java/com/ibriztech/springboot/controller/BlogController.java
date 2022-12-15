package com.ibriztech.springboot.controller;

import com.ibriztech.springboot.dto.PostDto;
import com.ibriztech.springboot.service.PostService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@Controller
public class BlogController {
    private PostService postService;

    public BlogController(PostService postService) {
        this.postService = postService;
    }

    //handler method to handle /
    @GetMapping("/")
    public String viewBlogPosts(Model model){
        List<PostDto> postResponse = postService.findAllPosts();
        model.addAttribute("postResponse", postResponse);
        return "blog/view_posts";
    }

    //handler method to handle view
    @GetMapping("/post/{postUrl}")
    private String showPost(@PathVariable("postUrl") String postUrl, Model model){
        PostDto post = postService.findPostByUrl(postUrl);
        model.addAttribute("post", post);
        return "blog/blog_post";

    }

}
