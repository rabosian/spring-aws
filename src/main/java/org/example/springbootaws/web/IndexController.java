package org.example.springbootaws.web;

import lombok.RequiredArgsConstructor;
import org.example.springbootaws.config.auth.dto.SessionUser;
import org.example.springbootaws.domain.posts.Posts;
import org.example.springbootaws.service.posts.PostsService;
import org.example.springbootaws.web.dto.PostsResponseDto;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import javax.servlet.http.HttpSession;

@RequiredArgsConstructor
@Controller
public class IndexController {
    private final PostsService postsService;
    private final HttpSession httpSession;

    @GetMapping("/")
    public String index(Model model) {
        // Model
        // defines a holder for model attributes. Primarily designed for adding attributes to the model.
        // Allows for accessing the overall model
        // In this case, the return object from .findAllDesc() is sent to index.mustache as a name "posts"
        model.addAttribute("posts", postsService.findAllDesc());

        SessionUser user = (SessionUser) httpSession.getAttribute("user");

        if (user != null) {
            model.addAttribute("userName", user.getName());
        }
        // when controller returns String,
        // mustache starter automatically sets the path and file extension as:
        // src/main/resources/templates/(filename).mustache, in this case, index.mustache
        return "index";
    }

    @GetMapping("/posts/save")
    public String postsSave() {
        return "posts-save";
    }

    @GetMapping("/posts/update/{id}")
    public String postsUpdate(@PathVariable Long id, Model model) {
        PostsResponseDto dto = postsService.findById(id);
        model.addAttribute("post", dto);
        return "posts-update";
    }
}
