package kunuz.uz.api.config;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.servlet.HandlerExceptionResolver;

import java.util.Arrays;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private final UserDetailsService userDetailsService;

    private final BCryptPasswordEncoder passwordEncoder;

    private final HandlerExceptionResolver exceptionResolver;

    public SecurityConfig(UserDetailsService userDetailsService,
                          BCryptPasswordEncoder passwordEncoder,
                          @Qualifier("handlerExceptionResolver") HandlerExceptionResolver exceptionResolver) {
        this.userDetailsService = userDetailsService;
        this.passwordEncoder = passwordEncoder;
        this.exceptionResolver = exceptionResolver;
    }

    public static final String[] AUTH_WHITELIST = {
            "/api/v1/auth/login",
            "/api/v1/auth/registration"
    };

    @Bean
    public AuthenticationProvider authenticationProvider() {
        final DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider(userDetailsService);
        authenticationProvider.setPasswordEncoder(passwordEncoder);
        return authenticationProvider;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        var jwtAuthenticationFilter = new JwtAuthenticationFilter(userDetailsService, exceptionResolver);
        var languageFilter = new LanguageFilter();

        http.authorizeHttpRequests(
                        authorizationManagerRequestMatcherRegistry -> {
                            authorizationManagerRequestMatcherRegistry
                                    .requestMatchers(AUTH_WHITELIST).permitAll()
                                    .anyRequest()
                                    .authenticated();
                        })
                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)
                .addFilterBefore(languageFilter, JwtAuthenticationFilter.class);

        http.csrf(AbstractHttpConfigurer::disable);

        http.cors(httpSecurityCorsConfigurer -> { // cors konfiguratsiya qilingan
            CorsConfiguration configuration = new CorsConfiguration();
            configuration.setAllowedOriginPatterns(Arrays.asList("*"));
            configuration.setAllowedMethods(Arrays.asList("*"));
            configuration.setAllowedHeaders(Arrays.asList("*"));

            UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
            source.registerCorsConfiguration("/**", configuration);
            httpSecurityCorsConfigurer.configurationSource(source);
        });
        return http.build();
    }


    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }
}
