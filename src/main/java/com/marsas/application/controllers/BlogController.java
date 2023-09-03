package com.marsas.application.controllers;

import com.marsas.application.models.Post;
import com.marsas.application.repositories.PostRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Controller
public class BlogController {

    private final PostRepository postRepository;

    public BlogController(PostRepository postRepository) {
        this.postRepository = postRepository;
    }

    @GetMapping("/blog")
    public String blogMain(Model model) {
        model.addAttribute("title", "Blog main");

        Iterable<Post> posts = postRepository.findAll();

        model.addAttribute("posts", posts);

        return "blog-main";
    }

    @GetMapping("/blog/add")
    public String blogAdd(Model model) {
        model.addAttribute("title", "Blog add");
        return "blog-add";
    }

    @PostMapping("/blog/add")
    public String blogPostAdd(@RequestParam String title,
                              @RequestParam String announce,
                              @RequestParam String fulltext,
                              Model model) {
        Post post = new Post(title, announce, fulltext);
        postRepository.save(post);
        return "redirect:/blog";
    }

    @GetMapping("/blog/{id}")
    public String blogDetails(@PathVariable(value = "id") long id, Model model) {
        if (!postRepository.existsById(id)){
            return "redirect:/blog";
        }
        model.addAttribute("title", id);
        Optional<Post> byId = postRepository.findById(id);
        List<Post> res = new ArrayList<>();
        byId.ifPresent(res::add);
        model.addAttribute("post", res);
        return "blog-details";
    }

    @GetMapping("/blog/{id}/edit")
    public String blogEdit(@PathVariable(value = "id") long id, Model model) {
        if (!postRepository.existsById(id)){
            return "redirect:/blog";
        }
        model.addAttribute("title", id);
        Optional<Post> byId = postRepository.findById(id);
        List<Post> res = new ArrayList<>();
        byId.ifPresent(res::add);
        model.addAttribute("post", res);
        return "blog-edit";
    }

    @PostMapping("/blog/{id}/edit")
    public String blogPostEdit(@PathVariable(value = "id") long id,
                               @RequestParam String title,
                               @RequestParam String announce,
                               @RequestParam String fulltext,
                               Model model) {
        if (!postRepository.existsById(id)){
            return "redirect:/blog";
        }
        model.addAttribute("title", id);
        Post byId = postRepository.findById(id).orElseThrow();
        byId.setTitle(title);
        byId.setAnnounce(announce);
        byId.setFullText(fulltext);

        postRepository.save(byId);
        return "redirect:/blog";
    }

    @PostMapping("/blog/{id}/remove")
    public String blogDelete(@PathVariable(value = "id") long id, Model model) {
        if (!postRepository.existsById(id)){
            return "redirect:/blog";
        }
        model.addAttribute("title", id);
        Post byId = postRepository.findById(id).orElseThrow();

        postRepository.delete(byId);
        return "redirect:/blog";
    }
}
