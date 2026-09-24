package com.nimesh.personal_blog.service;

import com.nimesh.personal_blog.dto.ArticleRequest;
import com.nimesh.personal_blog.dto.ArticleResponse;
import com.nimesh.personal_blog.entity.Article;
import com.nimesh.personal_blog.exception.ArticleNotFoundException;
import com.nimesh.personal_blog.repository.ArticleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ArticleServiceImpl implements ArticleService{
    private final ArticleRepository articleRepository;
    private final HtmlSanitizer htmlSanitizer;

    @Override
    public List<ArticleResponse> getAllArticles() {
        return articleRepository.findAllByOrderByPinnedDescPublicationDateAscIdAsc().stream().map(article -> new ArticleResponse(
                article.getId(), article.getTitle(), article.getContent(), article.getPublicationDate(), article.isPinned()
        ))
                .toList();
    }

    @Override
    public ArticleResponse getArticleById(Long id) {
        Article article = articleRepository.findById(id)
                .orElseThrow(() ->
                        new ArticleNotFoundException(
                                "Article not found with id: " + id
                        )
                );
        return new ArticleResponse(
                article.getId(),
                article.getTitle(),
                article.getContent(),
                article.getPublicationDate(),
                article.isPinned()
        );
    }

    @Override
    public ArticleResponse createArticle(ArticleRequest articleRequest) {
        Article article = new Article();

        article.setTitle(articleRequest.getTitle());
        article.setContent(htmlSanitizer.sanitize(articleRequest.getContent()));
        article.setPublicationDate(LocalDate.now());

        Article savedArticle = articleRepository.save(article);

        return new ArticleResponse(
                savedArticle.getId(),
                savedArticle.getTitle(),
                savedArticle.getContent(),
                savedArticle.getPublicationDate(),
                savedArticle.isPinned()
        );
    }

    @Override
    public ArticleResponse updateArticle(Long id, ArticleRequest articleRequest) {
        Article article = articleRepository.findById(id)
                .orElseThrow(() ->
                        new ArticleNotFoundException("Article not found with id: " + id)
                );

        article.setTitle(articleRequest.getTitle());
        article.setContent(htmlSanitizer.sanitize(articleRequest.getContent()));
        Article updatedArticle = articleRepository.save(article);

        return new ArticleResponse(
                updatedArticle.getId(),
                updatedArticle.getTitle(),
                updatedArticle.getContent(),
                updatedArticle.getPublicationDate(),
                updatedArticle.isPinned()
        );
    }

    @Override
    public void deleteArticle(Long id) {
        Article article = articleRepository.findById(id)
                .orElseThrow(() ->
                        new ArticleNotFoundException("Article not found with id: " + id)
                );

        articleRepository.delete(article);
    }

    @Override
    public void togglePinArticle(Long id) {
        Article article = articleRepository.findById(id)
                .orElseThrow(() ->
                        new ArticleNotFoundException("Article not found with id: " + id)
                );

        article.setPinned(!article.isPinned());
        articleRepository.save(article);
    }
}
