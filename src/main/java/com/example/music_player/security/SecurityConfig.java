package com.example.music_player.security;





import com.example.music_player.service.UserInfoDetailsService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {
    @Bean
    public UserDetailsService userDetailsService(){
        UserInfoDetailsService userInfoDetailsService=new UserInfoDetailsService();

        return userInfoDetailsService;
    }
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception {
        httpSecurity.authorizeHttpRequests( configurer->
                configurer.requestMatchers(HttpMethod.GET,"/api/v1/song/**").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.POST,"/api/v1/song").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.DELETE,"/api/v1/song/**").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.PUT,"/api/v1/song/**").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.GET,"/api/v1/playlist/**").hasAnyRole("NORMAL","ADMIN")
                        .requestMatchers(HttpMethod.POST,"/api/v1/playlist").hasAnyRole("NORMAL","ADMIN")
                        .requestMatchers(HttpMethod.DELETE,"/api/v1/playlist/**").hasAnyRole("NORMAL","ADMIN")
                        .requestMatchers(HttpMethod.PUT,"/api/v1/playlist/**").hasAnyRole("NORMAL","ADMIN")
                        .requestMatchers("/swagger-ui/**", "/v3/api-docs/**").permitAll()
                        .requestMatchers("/api/v1/user/**").permitAll()
        );
        httpSecurity.httpBasic();
        httpSecurity.csrf().disable().formLogin();
        return httpSecurity.build();
    }
    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();

    }
    @Bean
    public AuthenticationProvider authenticationProvider(){
        DaoAuthenticationProvider authenticationProvider=new DaoAuthenticationProvider();
        authenticationProvider.setUserDetailsService(userDetailsService());
        authenticationProvider.setPasswordEncoder(passwordEncoder());
        return authenticationProvider;
    }
}
