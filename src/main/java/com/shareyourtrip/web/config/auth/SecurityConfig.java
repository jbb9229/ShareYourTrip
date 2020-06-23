package com.shareyourtrip.web.config.auth;

import com.shareyourtrip.web.user.Role;
import lombok.RequiredArgsConstructor;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@RequiredArgsConstructor
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final CustomOAuth2UserService customOAuth2UserService;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
            // h2-console 화면을 사용하기 위해 disable
            .csrf().disable()
            .headers().frameOptions().disable()
            .and()
                // URL별 권한 관리
                .authorizeRequests()
                // 권한 관리 대상 지정
                // /api/** URL의 접속은 USER Role을 가지고있는 사람만 가능
                .antMatchers("/", "/css/**", "/images/**", "/js/**", "/h2-console/**", "/profile", "/posts/list", "/api/**").permitAll()
//                .antMatchers("/api/**").hasRole(Role.USER.name())
                // 이외의 URL은 인증된 사용자에게만 허용
                .anyRequest().authenticated()
            .and()
                // 로그아웃 성공시 리다이렉트 주소
                .logout()
                    .logoutSuccessUrl("/")
            .and()
                // OAuth2 로그인 기능의 설정 진입점
                .oauth2Login()
                    // 로그인 성공 이후 사용자의 정보를 가져올 때의 설정
                    .userInfoEndpoint()
                        // 소셜 로그인의 모든 프로세스 이후 동작할 UserService 구현체를 등록
                        .userService(customOAuth2UserService);
    }
}
