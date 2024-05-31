package dev.leonwong.config;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;

@Configuration
@EnableWebSecurity
public class AppConfig {

    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        //The difference between /* & /** is that the second matches the entire directory tree, including subdirectories, where as /* only matches at the level it's specified at.
        http.sessionManagement(management -> management.sessionCreationPolicy(SessionCreationPolicy.STATELESS)) // Disable session because we will be using JWT
                .authorizeHttpRequests(
                        Authorize -> Authorize
                                .requestMatchers("/api/admin/**").hasAnyRole("RESTAURANT_OWNER", "ADMIN")
                                .requestMatchers("/api/**").authenticated() // All user can access as long as he provides the JWT token
                                .anyRequest().permitAll() // All request other than /api/** will me permitted, user does not need JWT token, such as sign up and sign in
                ).addFilterBefore(new JwtTokenValidator(), BasicAuthenticationFilter.class).csrf(csrf -> csrf.disable()).cors(cors -> cors.configurationSource(corsConfigurationSource()));


        return null;
    }

    private CorsConfigurationSource corsConfigurationSource() {
        return new CorsConfigurationSource() {
            @Override
            public CorsConfiguration getCorsConfiguration(HttpServletRequest request) {
                CorsConfiguration cfg = new CorsConfiguration();
                // Set allowed origins for our frontend url
                cfg.setAllowedOrigins(Arrays.asList(
                        "http://localhost:3000"
                ));
                cfg.setAllowedMethods(Collections.singletonList("*")); // Create a list with only one element, and the list is immutable
                cfg.setAllowCredentials(true); // what is this
                cfg.setAllowedHeaders(Collections.singletonList("*"));
                cfg.setExposedHeaders(Arrays.asList("authorization"));
                cfg.setMaxAge(3600L); // What is this
                return cfg;
            }
        };
    }

    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
