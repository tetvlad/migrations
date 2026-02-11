package ru.netology.jdbc.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity(
        securedEnabled = true,        // Включаем @Secured
        jsr250Enabled = true,          // Включаем @RolesAllowed
        prePostEnabled = true          // Включаем @PreAuthorize/@PostAuthorize
)
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/products/public").permitAll()  // Публичный endpoint
                        .anyRequest().authenticated()  // Все остальные требуют авторизации
                )
                .formLogin(form -> form
                        .permitAll()  // Стандартная форма логина от Spring Security
                )
                .logout(logout -> logout
                        .permitAll()
                );

        return http.build();
    }

    @Bean
    public UserDetailsService userDetailsService() {
        // Пользователь с ролью READ
        UserDetails reader = User.builder()
                .username("reader")
                .password(passwordEncoder().encode("read123"))
                .roles("READ")
                .build();

        // Пользователь с ролью WRITE
        UserDetails writer = User.builder()
                .username("writer")
                .password(passwordEncoder().encode("write123"))
                .roles("WRITE")
                .build();

        // Пользователь с ролью DELETE
        UserDetails deleter = User.builder()
                .username("deleter")
                .password(passwordEncoder().encode("delete123"))
                .roles("DELETE")
                .build();

        // Пользователь с несколькими ролями
        UserDetails admin = User.builder()
                .username("admin")
                .password(passwordEncoder().encode("admin"))
                .roles("READ", "WRITE", "DELETE")
                .build();

        return new InMemoryUserDetailsManager(reader, writer, deleter, admin);
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}