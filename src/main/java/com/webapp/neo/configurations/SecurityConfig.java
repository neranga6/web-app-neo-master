package com.webapp.neo.configurations;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.web.SecurityFilterChain;


@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .authorizeRequests((authz) -> authz
                        // Publicly accessible URLs
                        .antMatchers("/public/**").permitAll()
                        .antMatchers("/css/**", "/images/**", "/js/**", "/ws", "/ws/**", "/h2/**" ,"resources/**","/api/publishQuote/**").permitAll()
                        .antMatchers("/my-page", "/qrcode/**", "/", "/chat/**", "/mes/", "/chat.sendMessage/**", "/topic/public/**",
                                "/chat.addUser/**", "/resume/**", "/resume", "/retrieve/**","/api/publishQuote/**").permitAll()
                        // Secured URLs with ADMIN role
                        .antMatchers("/pdf", "/pdf/**", "/resume", "/resume/**").hasRole("ADMIN")
                        .anyRequest().authenticated() // Other requests require authentication
                )
                .formLogin()
                .loginPage("/login")
                .defaultSuccessUrl("/resume-page", true) // Default page after login
                .permitAll()
                .and()
                .logout()
                .logoutSuccessUrl("/login") // Redirect to login on logout
                .permitAll()
                .and()
                .csrf().disable() // Disable CSRF protection if needed
                .headers().frameOptions().disable(); // Disable frame options if required (e.g., for H2 console)

        return http.build();
    }

    @Bean
    public WebSecurityCustomizer webSecurityCustomizer() {
        return (web) -> web.ignoring().antMatchers("/**");
    }
}