package EvaRuiz.HealthCarer.security;

import EvaRuiz.HealthCarer.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {




    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .authorizeRequests()
                .antMatchers("/", "/register", "/login").permitAll() // Allow public access to /login
                .anyRequest().authenticated()
                .and()
                .csrf().disable() // Disable CSRF temporary if necesary
                .formLogin()
                .loginPage("/") // Login form page
                .loginProcessingUrl("/login") // Endpoint where login is processed
                .defaultSuccessUrl("/index", true) // Redirect to /profile after successfull login
                .failureUrl("/loginerror") // Redirect to /loginerror if there is an error
                .permitAll()
                .and()
                .logout()
                .permitAll()  // Allow access to logout
                .logoutRequestMatcher(new AntPathRequestMatcher("/logout", "POST"))  // Ensure POST request
                .logoutSuccessUrl("/")  // Redirect to home after successful logout (without `?logout`)
                .clearAuthentication(true)  // Clear authentication on logout
                .invalidateHttpSession(true);  // Invalidate the session
        ;

    }



    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
