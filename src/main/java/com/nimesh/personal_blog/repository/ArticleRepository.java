package com.nimesh.personal_blog.repository;

import com.nimesh.personal_blog.entity.Article;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ArticleRepository extends JpaRepository<Article,Long> {
}
