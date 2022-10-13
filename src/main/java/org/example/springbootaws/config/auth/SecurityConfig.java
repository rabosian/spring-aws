package org.example.springbootaws.config.auth;

import lombok.RequiredArgsConstructor;
import org.example.springbootaws.domain.user.Role;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@RequiredArgsConstructor
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    private final CustomOAuth2UserService customOAuth2UserService;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable().headers().frameOptions().disable()
                .and()
                    .authorizeRequests()
                    // in order to use H2 console view
                    // "/" permit to all
                    // "/api/v1/**" permit only to USER
                    .antMatchers("/", "/css/**", "/images/**", "/js/**",  "/h2-console/**").permitAll()
                    .antMatchers("/api/v1/**").hasRole(Role.USER.name())
                    // any other request MUST BE authenticated(login)
                    .anyRequest().authenticated()
                .and()
                    .logout().logoutSuccessUrl("/")
                .and()
                    .oauth2Login()
                    .userInfoEndpoint()
                    .userService(customOAuth2UserService);
    }
}
