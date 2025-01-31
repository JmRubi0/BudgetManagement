package ca.vanier.budgetmanagement.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.config.Customizer;
 
@Configuration
public class ApplicationSecurityConfiguration {
 
    private final PasswordEncoder passwordEncoder;
 
    public ApplicationSecurityConfiguration(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }
 
    @SuppressWarnings({ "deprecation", "removal" })
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .csrf(csrf -> csrf.disable())
            .headers(headers -> headers.frameOptions(frameOptions -> frameOptions.disable()))
            .authorizeRequests(authorize -> authorize
                .requestMatchers("/index.html", "/greeting").permitAll()  // Use antMatchers instead
                .anyRequest().authenticated())  // Ensure that all other requests are authenticated
                .userDetailsService(userDetailsService())  // Explicitly setting UserDetailsService -- Line
            .httpBasic(Customizer.withDefaults())
            .formLogin(Customizer.withDefaults());
        return http.build();
    }
 
    @Bean
    public UserDetailsService userDetailsService() {
        UserDetails user = User.withUsername("student")
                .password(passwordEncoder.encode("password"))
                .roles("USER")
                .build();
        return new InMemoryUserDetailsManager(user);
    }
}
