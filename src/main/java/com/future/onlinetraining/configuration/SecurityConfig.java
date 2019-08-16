package com.future.onlinetraining.configuration;

import com.future.onlinetraining.service.UserService;
import com.google.common.collect.ImmutableList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    UserService userService;

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userService).passwordEncoder(new BCryptPasswordEncoder());
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .cors().and()
                .authorizeRequests()
                .antMatchers("/login", "/register", "/auth", "/unauthenticated").permitAll()
                .antMatchers("/v2/api-docs", "/swagger-resources/configuration/ui", "/swagger-resources",
                        "/swagger-resources/configuration/security",
                        "/swagger-ui.html", "/webjars/**").permitAll()
                .antMatchers("/_trainer/**").hasAnyAuthority("TRAINER", "ADMIN")
                .antMatchers("/_admin/**").hasAnyAuthority("ADMIN")
                .anyRequest().authenticated()
                .and()
                .formLogin()
                .loginPage("/auth")
                .failureUrl("/unauthenticated")
                .usernameParameter("email")
                .passwordParameter("password")
                .successHandler(this.loginSuccessHandler())
                .failureHandler(this.loginFailureHandler())
                .and()
                .logout().permitAll()
                .and().logout()
                .logoutUrl("/logout")
                .logoutSuccessUrl("/")
                .and()
                .csrf().disable();
    }

    protected AuthenticationSuccessHandler loginSuccessHandler(){
        return (request, response, authentication) -> {
            response.setStatus(1);
            response.sendRedirect("/auth/success");
        };
    }

    protected AuthenticationFailureHandler loginFailureHandler(){
        return (request, response, exception) -> {
            response.setStatus(0);
            response.sendRedirect("/auth/failed");
        };
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        final CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(ImmutableList.of("*"));
        configuration.setAllowedMethods(ImmutableList.of("*"));
        configuration.setAllowCredentials(true);
        configuration.setAllowedHeaders(ImmutableList.of("*"));
        final UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }

}