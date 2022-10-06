package org.example.springbootaws.web.domain.posts;

import org.example.springbootaws.domain.posts.Posts;
import org.example.springbootaws.domain.posts.PostsRepository;
import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;


@RunWith(SpringRunner.class)
@SpringBootTest // automatically run with H2
public class PostsRepositoryTest {
    @Autowired
    PostsRepository postsRepository;

    // @After
    // set method running after unit test
    // Used to prevent data breaches between tests when performing full tests before deployment
    @After
    public void cleanup() {
        postsRepository.deleteAll();
    }

    @Test
    public void callSavedPosts() {
        // given
        String title = "test title";
        String content = "test content";

        // run UPDATE when id exists, otherwise run INSERT
        postsRepository.save(Posts.builder()
                .title(title)
                .content(content)
                .author("rabosian")
                .build());

        // when
        List<Posts> postsList = postsRepository.findAll();

        // then
        Posts posts = postsList.get(0);
        assertThat(posts.getTitle()).isEqualTo(title);
        assertThat(posts.getContent()).isEqualTo(content);

    }

    @Test
    public void register_TimestampedEntity() {
        //given
        LocalDateTime now = LocalDateTime.of(2022,10,06,15,0,0);
        postsRepository.save(Posts.builder()
                .title("title1").content("content1").author("author1").build());

        //when
        List<Posts> postsList = postsRepository.findAll();

        //then
        Posts posts = postsList.get(0);
        System.out.println(">>>>>>>> createAt="+posts.getCreatedAt()+", modifiedAt="+posts.getModifiedAt());

        assertThat(posts.getCreatedAt()).isAfter(now);
        assertThat(posts.getModifiedAt()).isAfter(now);
    }
}
