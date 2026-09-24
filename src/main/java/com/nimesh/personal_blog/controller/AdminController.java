package com.nimesh.personal_blog.controller;

import com.nimesh.personal_blog.dto.ArticleRequest;
import com.nimesh.personal_blog.dto.ArticleResponse;
import com.nimesh.personal_blog.service.ArticleService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class AdminController {

    private final ArticleService articleService;
    @GetMapping("/admin")
    public String dashboard(Model model){

        List<ArticleResponse> articles = articleService.getAllArticles();

        model.addAttribute("articles",articles);

        return "admin/dashboard";
    }

    @GetMapping("/admin/new")
    public String showCreateArticleForm(Model model){
        model.addAttribute("article",new ArticleRequest());

        return "admin/new";
    }

    @PostMapping("/admin/new")
    public String createArticle(
            @Valid @ModelAttribute("article") ArticleRequest articleRequest,
            BindingResult bindingResult){

        if (bindingResult.hasErrors()){
            return "admin/new";
        }

        articleService.createArticle(articleRequest);

        return "redirect:/admin";
    }

    @GetMapping("/admin/edit/{id}")
    public String showEditArticleForm(@PathVariable Long id,Model model){
        ArticleResponse article = articleService.getArticleById(id);
        model.addAttribute("article",article);

        return "admin/edit";
    }

    //FYI
    //cant use patch cuz our UI uses a normal HTML form
    //Native HTML forms only support : GET , POST.
    //They don't directly support: PUT ,PATCH ,DELETE
    //we'd need to introduce method overriding or JavaScript to send an actual DELETE request.
    //For this project, that's unnecessary complexity ,so I'm are using POST.
    @PostMapping("/admin/edit/{id}")
    public String updateArticle(@PathVariable Long id,
                                @Valid @ModelAttribute("article") ArticleRequest articleRequest,
                                BindingResult bindingResult,Model model){
        if (bindingResult.hasErrors()){
            model.addAttribute("id",id);
            return "admin/edit";
        }
        articleService.updateArticle(id, articleRequest);

        return "redirect:/admin";
    }

    //FYI
    //cant use patch cuz our UI uses a normal HTML form
    //Native HTML forms only support : GET , POST.
    //They don't directly support: PUT ,PATCH ,DELETE
    //we'd need to introduce method overriding or JavaScript to send an actual DELETE request.
    //For this project, that's unnecessary complexity ,so I'm are using POST.
    @PostMapping("/admin/delete/{id}")
    public String deleteArticle(@PathVariable Long id){
        articleService.deleteArticle(id);

        return "redirect:/admin";
    }

    @PostMapping("admin/pin/{id}")
    public String togglePinArticle(@PathVariable Long id){
        articleService.togglePinArticle(id);
        return "redirect:/admin";
    }

}
