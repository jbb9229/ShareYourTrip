package com.shareyourtrip.web.config.auth;

import com.shareyourtrip.web.config.auth.dto.SessionUser;
import com.shareyourtrip.web.user.User;
import com.shareyourtrip.web.user.UserRepository;
import java.util.Collections;
import javax.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class CustomOAuth2UserService implements OAuth2UserService<OAuth2UserRequest, OAuth2User> {

    private final UserRepository userRepository;
    private final HttpSession httpSession;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        OAuth2UserService<OAuth2UserRequest, OAuth2User> delegate = new DefaultOAuth2UserService();
        OAuth2User oAuth2User = delegate.loadUser(userRequest);

        // 현재 로그인 진행 중인 서비스를 구분하는 코드
        // 구글만 사용하는 불필요 값이지만 이후 다른 소셜과의 구분을 위해 사용
        String registrationId = userRequest.getClientRegistration()
                                           .getRegistrationId();

        // OAuth2 로그인 진행 시 Key가 되는 필드값 (== Primary Key)
        String userNameAttributeName = userRequest.getClientRegistration()
                                                  .getProviderDetails()
                                                  .getUserInfoEndpoint()
                                                  .getUserNameAttributeName();

        // OAuth2UserService를 통해 가져온 OAuth2User의 attribute를 담은 클래스
        OAuthAttributes attributes = OAuthAttributes
                                    .of(registrationId, userNameAttributeName, oAuth2User.getAttributes());

        User user = saveOrUpdate(attributes);

        // 세션에 사용자 정보를 저장
        // User 객체를 사용하지 않고 SessionUser를 사용하는 이유는
        // User 클래스를 세션에 저장하려면 User 클래스의 직렬화가 필요한데
        // User 클래스는 Entity 이므로 직렬화 기능을 가진 세션DTO를 추가
        httpSession.setAttribute("user", new SessionUser(user));

        return new DefaultOAuth2User(Collections.singleton(new SimpleGrantedAuthority(user.getRoleKey())),
                                    attributes.getAttributes(),
                                    attributes.getNameAttributeKey());
    }

    private User saveOrUpdate(OAuthAttributes attributes) {
        User user = userRepository.findByEmail(attributes.getEmail())
                                  .map(entity -> entity.update(attributes.getName(), attributes.getPicture()))
                                  .orElse(attributes.toEntity());

        return userRepository.save(user);
    }

}
