package fashion.oboshie.config.filters;

import fashion.oboshie.models.AppUser;
import fashion.oboshie.services.AppUserService;
import fashion.oboshie.utils.JwtUtil;
import lombok.AllArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
@AllArgsConstructor
public class JwtRequestFilter extends OncePerRequestFilter {
    private final String AUTHORIZATION_HEADER_STARTER = "Oboshie ";
    private  final JwtUtil jwtUtil;
    private final AppUserService appUserService;


    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        final String authorizationHeader = request.getHeader("Authorization");
        String email = null, jwtToken = null;

        if (authorizationHeader != null && authorizationHeader.startsWith(AUTHORIZATION_HEADER_STARTER)){
            jwtToken = authorizationHeader.substring(AUTHORIZATION_HEADER_STARTER.length());
            email = jwtUtil.extractEmail(jwtToken);
        }

        if (email != null && SecurityContextHolder.getContext().getAuthentication() == null){
            AppUser user = (AppUser) this.appUserService.loadUserByUsername(email);
            if (jwtUtil.validateToken(jwtToken, user)){
                UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(email, null, user.getAuthorities());
                usernamePasswordAuthenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
            }
        }
        filterChain.doFilter(request, response);
    }
}
