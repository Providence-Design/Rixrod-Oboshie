package fashion.oboshie.utils;

import fashion.oboshie.models.AppUser;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.Map;
import java.util.UUID;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class JwtUtil {
    private final String SECRET_KEY = "jwt-secret";
    private final long ACCESS_TOKEN_EXPIRATION_LIMIT = 1000 * 60 * 60 * 10;
    private final long REFRESH_TOKEN_EXPIRATION_LIMIT = ACCESS_TOKEN_EXPIRATION_LIMIT * 3;

    public String extractEmail(String token){
        return extractClaim(token, Claims::getSubject);
    }

    public Date extractExpiration(String token){
        return extractClaim(token, Claims::getExpiration);
    }

    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver){
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    private Claims extractAllClaims(String token){
        return Jwts.parser().setSigningKey(SECRET_KEY).parseClaimsJws(token).getBody();
    }

    private Boolean isTokenExpired(String token){
        return extractExpiration(token).before(new Date());
    }

    public Map<String, String> generateTokens(AppUser appUser, HttpServletRequest request){
        String accessToken = createToken(appUser, request);
        String refreshToken = createRefreshToken(appUser, request);
        Map<String, String> tokens = Map.of("access_token", accessToken, "refresh_token", refreshToken);
        return tokens;
    }

    private String createToken(AppUser appUser, HttpServletRequest request) {
            return Jwts.builder()
                .claim("user", appUser)
                .claim("roles", appUser.getAuthorities().stream().map(GrantedAuthority::getAuthority).collect(Collectors.toList()))
                .setSubject(appUser.getEmail())
                .setIssuer(request.getRequestURI().toString())
                .setAudience("audience")
                .setId(UUID.randomUUID().toString())
                .setIssuedAt(new Date()) // for example, now
                .setExpiration(new Date(System.currentTimeMillis() + ACCESS_TOKEN_EXPIRATION_LIMIT))
                .setNotBefore(new Date()) //a java.util.Date
                .setId(UUID.randomUUID().toString()) //just an example id
                .signWith(SignatureAlgorithm.HS256, SECRET_KEY).compact();
    }

    private String createRefreshToken(AppUser appUser, HttpServletRequest request) {
        return Jwts.builder()
                .setSubject(appUser.getEmail())
                .setIssuer(request.getRequestURI().toString())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + REFRESH_TOKEN_EXPIRATION_LIMIT))
                .signWith(SignatureAlgorithm.HS256, SECRET_KEY).compact();
    }

    public boolean validateToken(String token, AppUser appUser){
        final String email = extractEmail(token);
        return (email.equals(appUser.getEmail()) && !isTokenExpired(token));
    }
}
