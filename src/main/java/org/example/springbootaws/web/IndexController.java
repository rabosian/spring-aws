package org.example.springbootaws.web;

import lombok.RequiredArgsConstructor;
import org.example.springbootaws.service.posts.PostsService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@RequiredArgsConstructor
@Controller
public class IndexController {
    private final PostsService postsService;
    @GetMapping("/")
    public String index(Model model) {
        model.addAttribute("posts", postsService.findAllDesc());
        // when controller returns String,
        // mustache starter automatically sets the path and file extension as:
        // src/main/resources/templates/(filename).mustache, in this case, index.mustache
        return "index";
    }
    // Model
    // defines a holder for model attributes. Primarily designed for adding attributes to the model.
    // Allows for accessing the overall model
    // In this case, the return object from .findAllDesc() is sent to index.mustache as a name "posts"

    @GetMapping("/posts/save")
    public String postsSave() {
        return "posts-save";
    }
}
