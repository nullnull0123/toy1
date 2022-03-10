package com.example.demo.security.config;

import lombok.RequiredArgsConstructor;
import com.example.demo.members.service.MemberService;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@RequiredArgsConstructor
@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final BCryptPasswordEncoder encoder;
    private final MemberService service;

    @Override

    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable();
        http.headers().frameOptions().disable();

        //인가
        http.authorizeRequests().antMatchers("/", "/css/**", "/images/**", "/js/**", "/h2-console/**").permitAll();
        http.authorizeRequests().antMatchers("/login").permitAll()
                .antMatchers("/logout").permitAll();
        http.authorizeRequests().antMatchers("/article/write", "/article/**/edit", "/article/**/delete").authenticated();

        // 인증
        // TODO : formLogin() 설정 완성
        http.formLogin()
                .defaultSuccessUrl("/article")
                .loginPage("/login")
                ;

        // TODO : logout() 설정 완성
        http.logout()
                .logoutUrl("/logout")
                .logoutSuccessUrl("/article")
                .invalidateHttpSession(true)
                .deleteCookies("JSESSIONID", "save_id", "save_pass")
                .clearAuthentication(true);
    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        // 보안 필터를 적용할 필요가 없는 리소스를 설정 js, css, images
        web.ignoring().requestMatchers(PathRequest.toStaticResources().atCommonLocations());
    }

    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(service);
        String password = encoder.encode("1111");
        auth.inMemoryAuthentication().withUser("user").password(password).roles("USER");
        auth.inMemoryAuthentication().withUser("magager").password(password).roles("MAGAGER");
        auth.inMemoryAuthentication().withUser("admin").password(password).roles("ADMIN");
    }
}
