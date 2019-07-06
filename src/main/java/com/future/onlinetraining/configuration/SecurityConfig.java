package com.future.onlinetraining.configuration;

import com.future.onlinetraining.users.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    UserService userService;

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userService).passwordEncoder(new BCryptPasswordEncoder());
    }


    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().antMatchers("/css/**");
        web.ignoring().antMatchers("/profile/pict/**");
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .authorizeRequests()
                .antMatchers("/login", "/register").permitAll()
                .and()
                .formLogin()
                .loginPage("/auth")
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

}