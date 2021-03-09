package com.example.demo.config;

import com.example.demo.security.JwtAuthenticationEntryPoint;
import com.example.demo.security.JwtAuthenticationTokenFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

//import io.swagger.models.HttpMethod;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {


//   private  UserDetailsService userDetailsService;
//
//   private  PasswordEncoder passwordEncoder;
//
//   private  JwtAuthenticationEntryPoint unauthorizedHandler;
//
//   @Autowired
//    public WebSecurityConfig(UserDetailsService userDetailsService, PasswordEncoder passwordEncoder, JwtAuthenticationEntryPoint unauthorizedHandler) {
//        this.userDetailsService = userDetailsService;
//        this.passwordEncoder = passwordEncoder;
//        this.unauthorizedHandler = unauthorizedHandler;
//    }


    @Autowired
    private UserDetailsService userDetailsService;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private JwtAuthenticationEntryPoint unauthorizedHandler;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable()
                // don't create session
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .exceptionHandling().authenticationEntryPoint(unauthorizedHandler)
                .and()
                .authorizeRequests()
                .antMatchers(HttpMethod.POST, "/users/genres").hasAuthority("ADMIN")
                .antMatchers(HttpMethod.POST, "/users/admin/users").hasAuthority("ADMIN")
                .antMatchers(HttpMethod.POST,"books/genre/*/author/*").hasAnyAuthority("ADMIN,USER,EDITOR")
                .antMatchers(HttpMethod.POST,"ratings/*/rate").hasAnyAuthority("ADMIN,USER,EDITOR")
//                .antMatchers(HttpMethod.POST,"/users").hasAuthority("ADMIN")
//                 .antMatchers(HttpMethod.PUT, "/users").hasAnyAuthority("USER", "ADMIN")
//               .antMatchers(HttpMethod.DELETE, "/users/*").hasAnyAuthority( "ADMIN")
//               .antMatchers(HttpMethod.DELETE, "/book/*").hasAnyAuthority( "ADMIN")
                .anyRequest().permitAll();

        // Custom JWT based security filter
        http
                .addFilterBefore(authenticationTokenFilterBean(), UsernamePasswordAuthenticationFilter.class);

        // disable page caching
        http.headers().cacheControl();
    }


    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth
                .userDetailsService(userDetailsService)
                .passwordEncoder(passwordEncoder);
    }

    @Bean
    public PasswordEncoder encoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public JwtAuthenticationTokenFilter authenticationTokenFilterBean() throws Exception {
        return new JwtAuthenticationTokenFilter();
    }


}

