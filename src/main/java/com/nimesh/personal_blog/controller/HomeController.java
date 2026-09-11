package com.nimesh.personal_blog.controller;

import com.nimesh.personal_blog.dto.ArticleResponse;
import com.nimesh.personal_blog.service.ArticleService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class HomeController {

    private final ArticleService articleService;

    @GetMapping("/")
    public String home(Model model){

        List<ArticleResponse> articles = articleService.getAllArticles();

        model.addAttribute("articles", articles);

        return "home";
    }
}
