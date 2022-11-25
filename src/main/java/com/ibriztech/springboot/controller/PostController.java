package com.ibriztech.springboot.controller;

import com.ibriztech.springboot.dto.PostDto;
import com.ibriztech.springboot.service.PostService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;
import java.util.Locale;

@Controller
public class PostController {

    private PostService postService;

    public PostController(PostService postService) {
        this.postService = postService;
    }

    // create handler method, GET request and return model and view
    @GetMapping("/admin/posts")
    public String posts(Model model){
        List<PostDto> posts = postService.findAllPosts();
        model.addAttribute("posts", posts);
        return "/admin/posts";
    }

    //handler method to handle new post request
    @GetMapping("/admin/newpost")
    public String newPostForm(Model model){
        PostDto postDto = new PostDto();
        model.addAttribute("post", postDto);
        return "admin/create_post";
    }
    //handler method to handle form submit request
    @PostMapping("/admin/posts")
    public String cratePost(@Valid  @ModelAttribute("post") PostDto postDto,
                            BindingResult result,
                            Model model){
        if(result.hasErrors()){
            model.addAttribute("post", postDto);
            return "admin/create_post";
        }
        postDto.setUrl(getUrl(postDto.getTitle()));
        postService.createPost(postDto);
        return "redirect:/admin/posts";
    }

    //handler method to handle edit post request
    @GetMapping("/admin/posts/{postId}/edit")
    public String editPostForm(@PathVariable("postId") Long postId, Model model){
        PostDto postDto = postService.findPostById(postId);
        model.addAttribute("post", postDto);
        return "admin/edit_post";
    }

    // handler method to handle edit post form submit request
    @PostMapping("/admin/posts/{postId}")
    public String updatePost(@PathVariable("postId") Long postId,
                            @Valid @ModelAttribute("post") PostDto post,
                             BindingResult result,
                             Model model){

        if(result.hasErrors()){
            model.addAttribute("post",post);
            return "admin/edit_post";
        }
        post.setId(postId);
        postService.updatePost(post);
        return "redirect:/admin/posts";
    }

    //handler method to handle delete post request
    @GetMapping("/admin/posts/{postId}/delete")
    public String deletePost(@PathVariable("postId") Long postId){
        postService.deletePost(postId);
        return "redirect:/admin/posts";
    }

    //returns post url in formatted form
    private static String getUrl(String postTitle){
        String title = postTitle.trim().toLowerCase();
        String url = title.replace("\\s+", "-");
        url = url.replaceAll("[^A-Za-z0-9]", "-");
        return url;
    }
}
