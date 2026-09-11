package com.nimesh.personal_blog.service;

import com.nimesh.personal_blog.dto.ArticleResponse;

import java.util.List;

public interface ArticleService {

    List<ArticleResponse> getAllArticles();
    ArticleResponse getArticleById(Long id);
}
