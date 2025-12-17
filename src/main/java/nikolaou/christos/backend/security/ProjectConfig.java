package nikolaou.christos.backend.security;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;

import java.util.List;

@Configuration
public class ProjectConfig {

    @Value("${endpoints.frontend.url}")
    private String frontendUrl;


    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        return http

                .authorizeHttpRequests(req -> req.anyRequest().permitAll())
                .csrf(AbstractHttpConfigurer::disable)
                .cors(cors -> cors.configurationSource(new CorsConfigurationSource() {
                    @Override
                    public CorsConfiguration getCorsConfiguration(HttpServletRequest request) {
                        return corsConfiguration();
                    }
                }))

                .build();
    }

    @Bean
    public CorsConfiguration corsConfiguration() {
        final CorsConfiguration corsConfiguration = new CorsConfiguration();

        corsConfiguration.setAllowedOrigins(List.of(frontendUrl));

        return corsConfiguration;
    }

}
