package com.grlife.rela_prog;

import com.grlife.rela_prog.service.LoginService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.header.writers.StaticHeadersWriter;

@EnableWebSecurity
@RequiredArgsConstructor
@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    private final LoginService loginService;

    @Override
    public void configure(WebSecurity web) { // static 하위 파일 목록(css, js, img) 인증 무시
        web.ignoring().antMatchers("/css/**", "/js/**", "/img/**", "/h2-console/**");
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception { // http 관련 인증 설정
        http
                .authorizeRequests() // 접근에 대한 인증 설정
                .antMatchers("/api/getInfo", "/login", "/signup", "/save").permitAll() // 누구나 접근 허용
                .antMatchers("/").hasAnyRole("USER") // USER, ADMIN만 접근 가능
                .anyRequest().authenticated() // 나머지 요청들은 권한의 종류에 상관 없이 권한이 있어야 접근 가능
                .and()
                .formLogin() // 로그인에 관한 설정
                .defaultSuccessUrl("/m/relation_group") // 로그인 성공 후 리다이렉트 주소
                .and()
                .logout() // 로그아웃
                .invalidateHttpSession(true) // 세션 날리기
        ;

        /* 접근 가능 외부 사이트 */
        http.headers().frameOptions().disable()
                .addHeaderWriter(new StaticHeadersWriter("X-FRAME-OPTIONS", "ALLOW-FROM https://grlife.tistory.com/"));
    }

    @Override
    public void configure(AuthenticationManagerBuilder auth) throws Exception { // 필요한 정보들을 가져오는 곳
        auth.userDetailsService(loginService).passwordEncoder(new BCryptPasswordEncoder()); // 해당 서비스(userService)에서는 UserDetailsService를 implements해서 loadUserByUsername() 구현해야함 (서비스 참고)
    }

}
