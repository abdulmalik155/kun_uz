package kunuz.uz.api.config;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import kunuz.uz.api.dto.request.JwtDTO;
import kunuz.uz.api.enums.TokenType;
import kunuz.uz.api.exceptions.InvalidJwtException;
import kunuz.uz.api.exceptions.WrongTokenTypeException;
import kunuz.uz.api.util.SecurityUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.servlet.HandlerExceptionResolver;

import java.io.IOException;
import java.util.Arrays;

@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final UserDetailsService userDetailsService;

    private final HandlerExceptionResolver resolver;

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
        AntPathMatcher pathMatcher = new AntPathMatcher();
        return Arrays
                .stream(SecurityConfig.AUTH_WHITELIST)
                .anyMatch(path -> pathMatcher.match(path, request.getServletPath()));
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {
        final String header = request.getHeader("Authorization");
        if (header == null || !header.startsWith("Bearer ")) {
            filterChain.doFilter(request, response);
            return;
        }
        try {
            JwtDTO jwtDto = SecurityUtil.profile(header);
            if(!jwtDto.getTokenType().equals(TokenType.ACCESS)){
                if(!request.getServletPath().equals("/api/v1/auth/new-access-token")){
                    throw new WrongTokenTypeException("Wrong token type");
                }
            }
            UserDetails userDetails = userDetailsService.loadUserByUsername(jwtDto.getUsername());

            UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
            authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

            SecurityContextHolder.getContext().setAuthentication(authenticationToken);
            filterChain.doFilter(request, response);
        } catch (InvalidJwtException | UsernameNotFoundException e) {
            filterChain.doFilter(request, response);
            return;
        } catch (WrongTokenTypeException e){
            resolver.resolveException(request,response,null,e);
            return;
        }
    }
}
