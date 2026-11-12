package kunuz.uz.api.config;

import kunuz.uz.api.service.JwtService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class JwtConfig {

    @Bean
    public JwtService jwtService(){
        return new JwtService();
    }
}
