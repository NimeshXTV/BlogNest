package com.nimesh.personal_blog.controller;

import com.nimesh.personal_blog.dto.ArticleResponse;
import com.nimesh.personal_blog.service.ArticleService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequiredArgsConstructor
public class ArticleController {
    private final ArticleService articleService;

    @GetMapping("/articles/{id}")
    public String getArticle(@PathVariable Long id,
                             @RequestParam(required = false) String from,
                             Model model){
        ArticleResponse article = articleService.getArticleById(id);

        model.addAttribute("article",article);
        model.addAttribute("fromAdmin","admin".equals(from));

        return "article";
    }
}
