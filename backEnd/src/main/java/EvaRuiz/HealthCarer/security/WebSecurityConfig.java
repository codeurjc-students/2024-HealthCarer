package EvaRuiz.HealthCarer.security;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import static org.springframework.http.HttpMethod.GET;
import static org.springframework.http.HttpMethod.POST;

@Configuration
@Order(Ordered.HIGHEST_PRECEDENCE + 1)
public class WebSecurityConfig {


    @Bean
    public SecurityFilterChain webSecurityFilterChain(HttpSecurity http, UserDetailsService userDetailsService) throws Exception {
        http
                .authorizeHttpRequests(auth ->
                        auth
                                .requestMatchers("/actuator", "/actuator/**").hasRole("ADMIN")
                                .requestMatchers(GET, "/css/**", "/js/**", "/images/**").permitAll() //static
                                .requestMatchers(GET, "/register").permitAll() // Permitir acceso público a /login
                                .requestMatchers(POST, "/register").permitAll() // Permitir acceso público a /login
                                .requestMatchers(GET, "/profile").authenticated()
                                .requestMatchers(GET, "/editProfile").authenticated()
                                .requestMatchers(POST, "/delete-account").authenticated()
                                .requestMatchers(POST, "/updateProfile").authenticated()

                                .anyRequest().permitAll()
                )
                .csrf(AbstractHttpConfigurer::disable) // Deshabilitar CSRF temporalmente si es necesario
                .formLogin(login ->
                        login
                                .loginPage("/") // Página del formulario de login
                                .loginProcessingUrl("/login") // Endpoint donde se procesa el login
                                .defaultSuccessUrl("/index", true) // Redirige a /profile tras el login exitoso
                                .failureUrl("/loginerror") // Redirige a /loginerror tras fallar
                                .permitAll() // Allow access to login
                )
                .logout(logout -> logout
                        .logoutRequestMatcher(new AntPathRequestMatcher("/logout", "POST"))// Ensure POST request
                        .permitAll() // Allow access to logout
                        .logoutSuccessUrl("/")  // Redirect to home after successful logout (without `?logout`)
                        .clearAuthentication(true)  // Clear authentication on logout
                        .invalidateHttpSession(true) // Invalidate the session)
                )
                .userDetailsService(userDetailsService)
        ;

        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
