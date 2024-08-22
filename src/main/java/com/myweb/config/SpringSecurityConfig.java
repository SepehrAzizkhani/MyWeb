package com.myweb.config;

import ch.qos.logback.core.Layout;
import com.myweb.madules.users.service.UsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.core.GrantedAuthorityDefaults;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.thymeleaf.spring5.SpringTemplateEngine;

import javax.sql.DataSource;

@Configuration
public class SpringSecurityConfig extends WebSecurityConfigurerAdapter{

@Autowired
private DataSource dataSource;

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.jdbcAuthentication().dataSource(dataSource)
                .passwordEncoder(NoOpPasswordEncoder.getInstance())
                .usersByUsernameQuery("select email,password,enabled from users_tbl where email=? ")
                .authoritiesByUsernameQuery("select email,roles from authorities where email=? ");
    }


    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable()
                .authorizeHttpRequests()
                .antMatchers("/Programming","/Products","/Celebrities","/more","/Artificial Intelligence","/UserRegister","/admin/**","/common/**","/index_statics/**","/posts/**","/img/**","/common/css/**",
                        "/common/js/**","/common/summernote/**","/admin/scss/**","/admin/js/**","/admin/dist/**",
                        "/index_statics/css/**","/index_statics/images/**","/index_statics/js/**","/common/summernote/CSS/**","/common/summernote/font/**"
                        ,"/common/summernote/lang/**","/common/summernote/plugin/databasic/**","/common/summernote/plugin/specialchars/**","/common/summernote/plugin/hello/**","https://cdn.jsdelivr.net/**","https://code.jquery.com/**",
                        "https://maxcdn.bootstrapcdn.com/**","https://www.googletagmanager.com/**","//pagead2.googlesyndication.com/**","https://stackpath.bootstrapcdn.com/**")
                .permitAll()
                .antMatchers("/dash","/dash/posts","/dash/posts//PostRegister").hasAnyAuthority("ADMIN","WRITER")
                .antMatchers("/dash","/dash/**").hasAuthority("ADMIN")
                .anyRequest().authenticated()
                .and().formLogin()
                .loginPage("/login").usernameParameter("email")
                .permitAll().and().logout().permitAll()
                .and()
                .exceptionHandling().accessDeniedPage("/403");
                http
                        .logout()
                        .logoutSuccessUrl("/");

    }
}
