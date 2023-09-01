package com.marsas.application.controllers;

import com.marsas.application.models.Post;
import com.marsas.application.repositories.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class BlogController {

    @Autowired
    private PostRepository postRepository;

    @GetMapping("/blog")
    public String blogMain(Model model) {
        model.addAttribute("title", "Blog main");

        Iterable<Post>posts = postRepository.findAll();

        model.addAttribute("posts", posts);

        return "blog-main";
    }
}
