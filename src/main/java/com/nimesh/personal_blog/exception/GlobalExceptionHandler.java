package com.nimesh.personal_blog.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

@ControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(ArticleNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ModelAndView handleArticleNotFound(ArticleNotFoundException exception){
        ModelAndView modelAndView = new ModelAndView(("error/404"));

        modelAndView.addObject("message",exception.getMessage());

        return modelAndView;
    }

}
