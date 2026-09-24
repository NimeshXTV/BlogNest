package com.nimesh.personal_blog.service;

import org.jsoup.Jsoup;
import org.jsoup.safety.Safelist;
import org.springframework.stereotype.Component;

@Component
public class HtmlSanitizer {

    public String sanitize(String html) {
        Safelist safelist = Safelist.relaxed()
                .addTags("h1", "h2", "h3")
                .addTags("pre", "code")
                .addAttributes("a", "target")
                .addAttributes("li", "data-list");

        return Jsoup.clean(html, safelist);
    }
}
