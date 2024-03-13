package news.newsphere.utils;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import java.io.IOException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.GenericFilterBean;

@RequiredArgsConstructor
public class JwtAuthenticationFilter extends GenericFilterBean {
    private final JwtUtil jwtUtil;

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        // 헤더에서 JWT 를 받아옵니다.
        String token = jwtUtil.resolveToken((HttpServletRequest) request);
        // SecurityContext에 이미 Authentication 객체가 설정되어 있는지 확인합니다.
        Authentication existingAuth = SecurityContextHolder.getContext().getAuthentication();
        // 유효한 토큰이 있고, 이미 인증된 사용자가 아니라면 인증 처리를 진행합니다.
        if (token != null && jwtUtil.validateToken(token) && (existingAuth == null || !existingAuth.isAuthenticated())) {
            // 토큰으로부터 유저 정보를 받아옵니다.
            Authentication authentication = jwtUtil.getAuthentication(token);
            // SecurityContext에 Authentication 객체를 저장합니다.
            SecurityContextHolder.getContext().setAuthentication(authentication);
        }
        chain.doFilter(request, response);
    }
}
//        // 유효한 토큰인지 확인합니다.
//        if (token != null && jwtUtil.validateToken(token)) {
//            // 토큰이 유효하면 토큰으로부터 유저 정보를 받아옵니다.
//            Authentication authentication = jwtUtil.getAuthentication(token);
//            // SecurityContext 에 Authentication 객체를 저장합니다.
//            SecurityContextHolder.getContext().setAuthentication(authentication);
//        }
