package com.as.booklibraryapp.security;

import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.sql.DataSource;

@EnableWebSecurity
@Configuration
@EnableGlobalMethodSecurity(securedEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final DataSource dataSource;

    public SecurityConfig(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth
                .jdbcAuthentication()
                .dataSource(dataSource)
                .passwordEncoder(passwordEncoder());
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .authorizeRequests()
                // url matching for access rights
                .requestMatchers(PathRequest.toStaticResources().atCommonLocations(), PathRequest.toH2Console()).permitAll()
                .antMatchers("/home", "/user/new", "/user/entryAfterRegistration").permitAll()
                .antMatchers("user/entryAfterLogin", "/user/newLoans", "/user/loans", "/confirm", "/user/search").authenticated()
                .antMatchers("/login").anonymous()
                .anyRequest().authenticated()
                //.and()
                //.csrf().ignoringAntMatchers("/h2-console/**", "/user/**", "/login")
                //.and()
                //.headers().frameOptions().sameOrigin()
                .and()
                .formLogin()
                    .loginPage("/login")
                    .defaultSuccessUrl("/user/entryAfterLogin", true)
                    .failureUrl("/login?error")
                .and()
                .logout()
                    .logoutSuccessUrl("/login?logout") //;
                .and()
                .csrf().ignoringRequestMatchers(PathRequest.toH2Console())
                       .ignoringAntMatchers("/api/**", "/login")
                .and()
                .headers().frameOptions().sameOrigin();

                //.csrf().ignoringRequestMatchers(PathRequest.toH2Console())
                //.and()
                // allow url from one page not others
    }
}
