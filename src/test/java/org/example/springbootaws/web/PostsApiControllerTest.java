package org.example.springbootaws.web;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.springbootaws.domain.posts.Posts;
import org.example.springbootaws.domain.posts.PostsRepository;
import org.example.springbootaws.web.dto.PostsSaveRequestDto;
import org.example.springbootaws.web.dto.PostsUpdateRequestDto;
import org.junit.Before;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.*;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
// when testing API controller along with JPA function testing,
// use @SpringBootTest and TestRestTemplate rather than @WebMvcTest
// since @WebMvcTest cannot run JPA
public class PostsApiControllerTest {
    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;
    @Autowired
    private PostsRepository postsRepository;

    @Autowired
    private WebApplicationContext context;
    private MockMvc mvc;

    // use MockMvc in SpringBootTest
    // create mvc instance before start test code
    @Before
    public void setup() {
        mvc = MockMvcBuilders
                .webAppContextSetup(context)
                .apply(springSecurity())
                .build();
    }

    @After
    public void tearDown() throws Exception {
        postsRepository.deleteAll();
    }

    @Test
    @WithMockUser(roles="USER")
    public void registerPosts() throws Exception {
        //given
        String title = "title1";
        String content = "content1";
        PostsSaveRequestDto requestDto = PostsSaveRequestDto.builder()
                .title(title)
                .content(content)
                .author("author1")
                .build();

        String url = "http://localhost:" + port + "/api/v1/posts";

        // without mock mvc
        // ResponseEntity<Long> responseEntity = restTemplate.postForEntity(url, requestDto, Long.class);
        // assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
        // assertThat(responseEntity.getBody()).isGreaterThan(0L);

        // when
        mvc.perform(post(url)
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(new ObjectMapper().writeValueAsString(requestDto)))
                .andExpect(status().isOk());

        // then
        List<Posts> all = postsRepository.findAll();
        assertThat(all.get(0).getTitle()).isEqualTo(title);
        assertThat(all.get(0).getContent()).isEqualTo(content);


    }

    @Test
    @WithMockUser(roles="USER")
    public void updatePosts() throws Exception {
        //given
        Posts savedPosts = postsRepository.save(Posts.builder()
                .title("title1")
                .content("content1")
                .author("author1")
                .build());
        Long savedPostsId = savedPosts.getId();
        String expectedTitle = "title2";
        String expectedContent = "content2";

        PostsUpdateRequestDto requestDto = PostsUpdateRequestDto.builder()
                .title(expectedTitle)
                .content(expectedContent)
                .build();

        String url = "http://localhost:" + port + "/api/v1/posts/" + savedPostsId;
        HttpEntity<PostsUpdateRequestDto> requestEntity = new HttpEntity<>(requestDto);

        // without mock mvc
        // ResponseEntity<Long> responseEntity = restTemplate.exchange(url, HttpMethod.PUT, requestEntity, Long.class);
        // assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
        // assertThat(responseEntity.getBody()).isGreaterThan(0L);

        // when
        mvc.perform(put(url)
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(new ObjectMapper().writeValueAsString(requestDto)))
                .andExpect(status().isOk());

        // then
        List<Posts> all = postsRepository.findAll();
        assertThat(all.get(0).getTitle()).isEqualTo(expectedTitle);
        assertThat(all.get(0).getContent()).isEqualTo(expectedContent);
    }
}
