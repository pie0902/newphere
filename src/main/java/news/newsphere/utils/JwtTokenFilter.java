package news.newsphere.utils;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import news.newsphere.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.GenericFilterBean;


public class JwtTokenFilter extends GenericFilterBean {
    @Autowired
    private JwtUtil jwtUtil;

    public JwtTokenFilter(JwtUtil jwtUtil) {
        this.jwtUtil = jwtUtil;
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain filterChain)
        throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;

        String authorization = httpRequest.getHeader("Authorization");
        // Authorization 헤더 검증
        if (authorization == null || !authorization.startsWith("Bearer ")) {
            filterChain.doFilter(request, response);
            return;
        }

        String token = authorization.substring(7); // "Bearer " 부분을 제거

        // 토큰 검증
        if (!jwtUtil.validateToken(token)) { // 토큰이 유효하지 않은 경우
            httpResponse.setStatus(HttpServletResponse.SC_UNAUTHORIZED); // 401 상태 코드 설정
            return; // 필터 체인 종료
        }

        // 토큰에서 사용자 정보를 추출하여 User 객체 획득
        User user = jwtUtil.getUserFromToken(token);
        if(user != null) {
            // User 객체를 기반으로 CustomUserDetails 객체 생성
            CustomUserDetails userDetails = new CustomUserDetails(user);
            // 사용자 인증 정보를 SecurityContext에 설정
            UsernamePasswordAuthenticationToken authentication =
                new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
            SecurityContextHolder.getContext().setAuthentication(authentication);
        } else {
            httpResponse.setStatus(HttpServletResponse.SC_UNAUTHORIZED); // User not found
            return;
        }

        filterChain.doFilter(request, response);
    }
}
