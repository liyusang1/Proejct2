package org.example.project2.global.springsecurity;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.example.project2.global.jwt.JwtProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

/**
 * @author liyusang1
 * @implNote 해당 클래스는 SimpleUrlAuthenticationSuccessHandler를 상속받은 OAuth 로그인 성공 후 로직을 처리 하는 클래스
 * 로그인 성공 후 리디렉트 하게 설정 했습니다.
 * 프론트 배포사이트 -> http://localhost:5173/auth/social
 * https://tripcometrue.vercel.app
 * 스프링 코드 내로 리디렉트 설정 하고 싶은 경우
 * String redirectUrl = "/user/oauth-success?token="+token;
 */
@Component
@RequiredArgsConstructor
public class OAuth2LoginSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

    private final JwtProvider jwtProvider;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
        Authentication authentication) throws IOException, ServletException {

        PrincipalDetails principalDetails = (PrincipalDetails) authentication.getPrincipal();
        String token = jwtProvider.createToken(principalDetails.getMember());
        Long memberId = principalDetails.getMember().getId();

/*        String email = principalDetails.getEmail();
        String name = principalDetails.getUsername();

        //한국어 인코딩 설정
        String encodedName = URLEncoder.encode(name, StandardCharsets.UTF_8.toString());*/
        // https://5b26-115-90-99-121.ngrok-free.app
        // String redirectUrl = "http://192.168.230.30:8081/auth/social?token="+token+"&memberId="+memberId;

        String redirectUrl = "https://3c78-115-90-99-121.ngrok-free.app/auth/social?token="+token+"&memberId="+memberId;
        getRedirectStrategy().sendRedirect(request, response, redirectUrl);
    }
}
