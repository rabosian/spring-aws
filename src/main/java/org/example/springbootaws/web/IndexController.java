package org.example.springbootaws.web;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@RequiredArgsConstructor
@Controller
public class IndexController {
    @GetMapping("/")
    public String index() {
        // when controller returns String,
        // mustache starter automatically sets the path and file extension as:
        // src/main/resources/templates/(filename).mustache, in this case, index.mustache
        return "index";
    }

    @GetMapping("/posts/save")
    public String postsSave() {
        return "posts-save";
    }
}
