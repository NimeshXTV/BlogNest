package com.nimesh.personal_blog.service;

import com.nimesh.personal_blog.dto.ArticleRequest;
import com.nimesh.personal_blog.dto.ArticleResponse;
import com.nimesh.personal_blog.entity.Article;

import java.util.List;

public interface ArticleService {

    List<ArticleResponse> getAllArticles();
    ArticleResponse getArticleById(Long id);
    ArticleResponse createArticle(ArticleRequest articleRequest);
    ArticleResponse updateArticle(Long id,ArticleRequest articleRequest);
    void deleteArticle(Long id);
    void togglePinArticle(Long id);
}
