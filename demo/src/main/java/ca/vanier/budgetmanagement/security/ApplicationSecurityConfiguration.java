package ca.vanier.budgetmanagement.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import ca.vanier.budgetmanagement.repository.UserRepository;

import org.springframework.security.config.Customizer;
 
/**
 * User Auth Security
 */
@Configuration
@EnableWebSecurity
public class ApplicationSecurityConfiguration {

    /**
     * from the index login to h2 console are all permitted to anyone (not necessary to be a user yet) 
     * 
     * To register is permitted to all
     * 
     * After creation of account either USER or ADMIN are able to access all endpoints with using basic auth and their crendentials on postman
     *
     * ADMIN only can acces the admin endpoints
     */
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http, UserDetailsService userDetailsService) throws Exception {
        http
            .csrf(AbstractHttpConfigurer::disable)
            .headers(headers -> headers
                .frameOptions(frameOptions -> frameOptions.sameOrigin()))
            .authorizeHttpRequests(auth -> auth
                .requestMatchers("/index.html", "/greeting").permitAll()
                .requestMatchers("/h2-console/**").permitAll()
                .requestMatchers("/users/register").permitAll()
                .requestMatchers("/budget/**").hasAnyRole("USER", "ADMIN")
                .requestMatchers("/admin/**").hasRole("ADMIN")
                .anyRequest().authenticated())
            .userDetailsService(userDetailsService)
            .httpBasic(Customizer.withDefaults())
            .formLogin(Customizer.withDefaults());
            
        return http.build();
    }

    /*
     * Checks user credentials if it is the correct user and password
     */
    @Bean
    public UserDetailsService userDetailsService(PasswordEncoder passwordEncoder, UserRepository userRepository) {
        return username -> {
            ca.vanier.budgetmanagement.entities.User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
 
            return org.springframework.security.core.userdetails.User.withUsername(user.getUsername())
                .password(user.getPassword())
                .roles(user.getRole().toUpperCase())
                .build();
        };
    }

    /**
     * 
     * @return Encrypted password in the database
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
