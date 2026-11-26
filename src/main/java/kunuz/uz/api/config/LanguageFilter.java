package kunuz.uz.api.config;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import kunuz.uz.api.enums.Language;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Arrays;
import java.util.Locale;

public class LanguageFilter extends OncePerRequestFilter {

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
        String header = request.getHeader("Accept-Language");

        Language appLanguage = Language.UZ;
        if(header != null && !header.isBlank()){
            final String normalizedLang = header.toLowerCase().trim();
            if(normalizedLang.equals("en")){
                appLanguage = Language.EN;
            }else if(normalizedLang.equals("ru")){
                appLanguage = Language.RU;
            }
        }

        LocaleContextHolder.setLocale(Locale.forLanguageTag(appLanguage.name()));

        try {
            filterChain.doFilter(request, response);
        }finally {
            LocaleContextHolder.resetLocaleContext();
        }
    }
}
