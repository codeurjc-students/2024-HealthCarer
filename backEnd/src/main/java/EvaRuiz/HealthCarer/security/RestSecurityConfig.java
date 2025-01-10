package EvaRuiz.HealthCarer.security;

import EvaRuiz.HealthCarer.security.jwt.JwtRequestFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;


@Configuration
@Order(Ordered.HIGHEST_PRECEDENCE)
@RequiredArgsConstructor
public class RestSecurityConfig {

    @Bean
    public SecurityFilterChain restSecurityFilterChain(HttpSecurity http, UserDetailsService userDetailsService, JwtRequestFilter jwtRequestFilter) throws Exception {
        http.securityMatcher("/api/**")
                .authorizeHttpRequests(auth -> auth
                        // URLs that need authentication to access to it
                        .requestMatchers(HttpMethod.POST, "/api/medications/**").authenticated()
                        .requestMatchers(HttpMethod.GET, "/api/medications/**").authenticated()
                        .requestMatchers(HttpMethod.PUT, "/api/medications/**").authenticated()
                        .requestMatchers(HttpMethod.DELETE, "/api/medications/**").authenticated()
                        .requestMatchers(HttpMethod.POST, "/api/treatments/**").authenticated()
                        .requestMatchers(HttpMethod.PUT, "/api/treatments/**").authenticated()
                        .requestMatchers(HttpMethod.DELETE, "/api/treatments/**").authenticated()
                        .requestMatchers(HttpMethod.GET, "/api/treatments/**").authenticated()
                        .requestMatchers(HttpMethod.POST, "/api/takes/**").authenticated()
                        .requestMatchers(HttpMethod.PUT, "/api/takes/**").authenticated()
                        .requestMatchers(HttpMethod.DELETE, "/api/takes/**").authenticated()
                        .requestMatchers(HttpMethod.GET, "/api/takes/**").authenticated()
                        .requestMatchers(HttpMethod.PUT, "/api/users/**").authenticated()
                        .requestMatchers(HttpMethod.GET, "/api/users/**").authenticated()
                        .requestMatchers(HttpMethod.DELETE, "/api/users/**").authenticated()

                        // Other URLs can be accessed without authentication
                        .anyRequest().permitAll())

                // Disable CSRF protection (it is difficult to implement in REST APIs)
                .csrf(AbstractHttpConfigurer::disable)

                // user configuration
                .userDetailsService(userDetailsService)

                // Disable Http Basic Authentication
                .httpBasic(Customizer.withDefaults())

                // Disable Form login Authentication
                .formLogin(AbstractHttpConfigurer::disable)

                // Avoid creating session
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))

                // Add JWT Token filter
                .addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }
}
