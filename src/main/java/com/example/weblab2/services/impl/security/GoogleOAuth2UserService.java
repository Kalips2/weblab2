package com.example.weblab2.services.impl.security;

import static com.example.weblab2.exceptions.Exceptions.NO_EMAIL;

import com.example.weblab2.dto.security.UserDto;
import com.example.weblab2.entities.User;
import com.example.weblab2.exceptions.InternalException;
import com.example.weblab2.mappers.UserMapper;
import com.example.weblab2.repositories.UserRepository;
import java.util.Objects;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

@Slf4j
@RequiredArgsConstructor
@Service
public class GoogleOAuth2UserService extends DefaultOAuth2UserService {
  private final UserRepository userRepository;
  // It's the method which will be called by Spring OAuth2 upon successful authentication!

  @Override
  public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
    log.info("Try to load user for Google");
    OAuth2User oAuth2User = super.loadUser(userRequest);
    String email = oAuth2User.getAttributes().get("email").toString();
    String username =  oAuth2User.getAttributes().get("name").toString();

    if (Objects.isNull(email) || email.isEmpty()) {
      throw new InternalException(NO_EMAIL);
    }

    Optional<User> userByEmail = userRepository.findByEmail(email);

    OAuth2User result;
    User user;
    user = userByEmail.orElseGet(() -> UserMapper.dtoToEntity(email, username));
    userRepository.save(user);
    result = UserDto.generate(user, oAuth2User.getAttributes());

    return result;
  }

}
