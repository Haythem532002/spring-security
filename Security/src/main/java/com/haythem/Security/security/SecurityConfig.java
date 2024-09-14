package com.haythem.Security.security;

import com.haythem.Security.user.Permission;
import com.haythem.Security.user.RoleEnum;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.logout.LogoutHandler;

import static org.springframework.security.config.Customizer.withDefaults;
import static org.springframework.security.config.http.SessionCreationPolicy.STATELESS;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
@EnableMethodSecurity(securedEnabled = true)
public class SecurityConfig {

    private final JwtFilter jwtAuthFilter;
    private final AuthenticationProvider authenticationProvider;
    private final LogoutHandler logoutHandler;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .cors(withDefaults())
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(req ->
                                req
                                        .requestMatchers(
                                                "/auth/**",
                                                "/v2/api-docs",
                                                "/v3/api-docs",
                                                "/v3/api-docs/**",
                                                "/swagger-resources",
                                                "/swagger-resources/**",
                                                "/configuration/ui",
                                                "/configuration/security",
                                                "/swagger-ui/**",
                                                "/webjars/**",
                                                "/swagger-ui.html"
                                        )
                                        .permitAll()

                                        .requestMatchers("/api/v1/managment/**")
                                        .hasAnyRole(RoleEnum.MANAGER.name(), RoleEnum.ADMIN.name())
                                        .requestMatchers(HttpMethod.GET, "/api/v1/managment/**")
                                        .hasAnyAuthority(Permission.ADMIN_READ.name(), Permission.MANAGMENT_READ.name())
                                        .requestMatchers(HttpMethod.POST, "/api/v1/managment/**")
                                        .hasAnyAuthority(Permission.ADMIN_CREATE.name(), Permission.MANAGMENT_CREATE.name())
                                        .requestMatchers(HttpMethod.PUT, "/api/v1/managment/**")
                                        .hasAnyAuthority(Permission.ADMIN_UPDATE.name(), Permission.MANAGMENT_UPDATE.name())
                                        .requestMatchers(HttpMethod.DELETE, "/api/v1/managment/**")
                                        .hasAnyAuthority(Permission.ADMIN_DELETE.name(), Permission.MANAGMENT_DELETE.name())

//                                .requestMatchers("/api/v1/admin/**")
//                                .hasRole(RoleEnum.ADMIN.name())
//                                .requestMatchers(HttpMethod.GET, "/api/v1/admin/**")
//                                .hasAuthority(Permission.ADMIN_READ.name())
//                                .requestMatchers(HttpMethod.POST, "/api/v1/admin/**")
//                                .hasAuthority(Permission.ADMIN_CREATE.name())
//                                .requestMatchers(HttpMethod.PUT, "/api/v1/admin/**")
//                                .hasAuthority(Permission.ADMIN_UPDATE.name())
//                                .requestMatchers(HttpMethod.DELETE, "/api/v1/admin/**")
//                                .hasAuthority(Permission.ADMIN_DELETE.name())

                                        .anyRequest()
                                        .authenticated()
                )
                .sessionManagement(session -> session.sessionCreationPolicy(STATELESS))
                .authenticationProvider(authenticationProvider)
                .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class)
                .logout((logout) ->
                        logout
                                .logoutUrl("/auth/logout")
                                .addLogoutHandler(logoutHandler)
                                .logoutSuccessHandler((request, response, authentication) ->
                                        SecurityContextHolder.clearContext()
                                )
                )

        ;

        return http.build();
    }
}
