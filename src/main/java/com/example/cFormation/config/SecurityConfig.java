package com.example.cFormation.config;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.SecurityFilterChain;
import com.example.cFormation.security.JwtAuthenticationFilter;
import com.example.cFormation.security.JwtUtil;


@Configuration
@EnableWebSecurity
public class SecurityConfig {



    private final JwtUtil jwtUtil;
    public SecurityConfig(JwtUtil jwtUtil) {
        this.jwtUtil = jwtUtil;
    }
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable()) // Désactive CSRF
                .authorizeHttpRequests(auth -> auth
                        //.requestMatchers("domaines","/roles","/employeurs","/formateurs","/formateurs/**","/utilisateurs","/utilisateurs/**","/participants","/participants/**", "/profiles/**","/structures" ,"/login", "/authenticate").permitAll() // Accès libre
                        .requestMatchers("/authenticate").permitAll()  // Accès libre
                        //.requestMatchers("/admin/**").hasRole("ADMIN") // Accès restreint
                        .anyRequest().authenticated() // Toute autre requête nécessite une authentification
                )
                .addFilterBefore(new JwtAuthenticationFilter(jwtUtil), UsernamePasswordAuthenticationFilter.class);  // Ajouter le filtre JWT avant le filtre d'authentification standard

        return http.build();
    }


    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}