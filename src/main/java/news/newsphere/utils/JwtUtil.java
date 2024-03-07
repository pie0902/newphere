package news.newsphere.utils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import jakarta.annotation.PostConstruct;
import java.util.Base64;
import java.util.Date;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;

@Component
public class JwtUtil {

    // 토큰을 생성하고 검증하는 클래스입니다.
// 해당 컴포넌트는 필터클래스에서 사전 검증을 거칩니다.
    @RequiredArgsConstructor
    @Component
    public class JwtTokenProvider {

        @Value("${jwt.secretKey}")
        private String secretKey;
        @Value("${jwt.expiredMs}")
        private long expiredMs;

        private final UserDetailsService userDetailsService;
        @PostConstruct
        protected void init() {
            secretKey = Base64.getEncoder().encodeToString(secretKey.getBytes());
        }
        // JWT 토큰 생성
        public String createToken(String userPk, List<String> roles) {
            Claims claims = Jwts.claims()
                .setSubject(userPk); // JWT payload 에 저장되는 정보단위, 보통 여기서 user를 식별하는 값을 넣는다.
            claims.put("roles", roles); // 정보는 key / value 쌍으로 저장된다.
            Date now = new Date();
            return Jwts.builder()
                .setClaims(claims) // 정보 저장
                .setIssuedAt(now) // 토큰 발행 시간 정보
                .setExpiration(new Date(now.getTime() + expiredMs)) // set Expire Time
                .signWith(SignatureAlgorithm.HS256,
                    secretKey)  // 사용할 암호화 알고리즘과 signature 에 들어갈 secret값 세팅
                .compact();
        }
        // JWT 토큰에서 인증 정보 조회
        public Authentication getAuthentication(String token) {
            UserDetails userDetails = userDetailsService.loadUserByUsername(this.getUserPk(token));
            return new UsernamePasswordAuthenticationToken(userDetails, "", userDetails.getAuthorities());
        }
    }
}
