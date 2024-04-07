//package com.example.gateway.config;
//
//import com.example.gateway.MyGlobalFilter;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
//import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
//import org.springframework.security.web.SecurityFilterChain;
//import org.springframework.web.cors.CorsConfiguration;
//import org.springframework.web.cors.CorsConfigurationSource;
//import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
//
//import java.util.List;
//
//@Configuration
//@EnableWebSecurity
//public class SecurityConfig {
//
//    @Bean
//    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
//        JwtAuthenticationConverter jwtAuthenticationConverter = new JwtAuthenticationConverter();
////        jwtAuthenticationConverter.setJwtGrantedAuthoritiesConverter(new KeycloakRealmRoleConverter());
//
//        http.csrf().disable();
//        http.cors((a) -> corsConfigurationSource())
//                .authorizeHttpRequests(authorizeRequests ->
//                        authorizeRequests.requestMatchers("/custom_login","/login").permitAll()
//                                .anyRequest().authenticated()
//                )
//                .oauth2ResourceServer(oauth2ResourceServer ->
//                        oauth2ResourceServer.jwt(jwt -> jwt.jwtAuthenticationConverter(jwtAuthenticationConverter)));
//        return http.build();
//    }
//
//    @Bean
//    CorsConfigurationSource corsConfigurationSource() {
//        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
//        CorsConfiguration configuration = new CorsConfiguration();
//        configuration.setAllowCredentials(true);
//        configuration.setAllowedOrigins(List.of("*"));
//        configuration.setAllowedMethods(List.of("*"));
//        configuration.setAllowedHeaders(List.of("*"));
//        configuration.setMaxAge(60L);
//        source.registerCorsConfiguration("/**",configuration);
//        return source;
//    }
//
//    @Bean
//    MyGlobalFilter globalFilter() {
//        return new MyGlobalFilter();
//    }
//}