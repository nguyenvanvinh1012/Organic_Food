package com.vanvinh.book_store.ultils;


import com.vanvinh.book_store.services.CustomUserDetailService;
import com.vanvinh.book_store.services.OAuthService;
import com.vanvinh.book_store.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.core.oidc.user.DefaultOidcUser;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity(securedEnabled = true, jsr250Enabled = true)
@RequiredArgsConstructor
public class SecurityConfig {
    private final UserService userService;
    private final OAuthService oAuthService;
    @Bean
    public UserDetailsService userDetailsService() {
        return new CustomUserDetailService();
    }
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider auth = new DaoAuthenticationProvider();
        auth.setUserDetailsService(userDetailsService());
        auth.setPasswordEncoder(passwordEncoder());
        return auth;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws
            Exception {
        return http.csrf().disable()
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/cart/checkout")
                        .authenticated()
                        .requestMatchers("/product/comment")
                        .authenticated()
                        .requestMatchers("/assets/**", "/client_assets/**", "/", "/register","/product-images/**", "/avt-images/**","/blog-images/**","/blog/detail/**","/sendEmail",
                                "/error","/product/**","/cart/**","/add-to-cart/**", "/webjars/jquery/3.6.4/jquery.min.js","/getComments","/getTotalComment",
                                "/total_items","/getCart","/getSumPrice","/wishlist/**","/add-to-wishlist/**","/getProduct/**","/search/**", "/about", "/contact")
                        .permitAll()
                        .requestMatchers( "/admin/**")
                        .hasAnyAuthority("ADMIN")
                        .anyRequest().authenticated()
                )
                .logout(logout -> logout.logoutUrl("/logout")
                        .logoutSuccessUrl("/login")
                        .deleteCookies("JSESSIONID")
                        .invalidateHttpSession(true)
                        .clearAuthentication(true)
                        .permitAll()

                )
                .formLogin(formLogin -> formLogin.loginPage("/login")
                        .loginProcessingUrl("/login")
                        .defaultSuccessUrl("/")
                        .permitAll()

                ).oauth2Login(
                        oauth2Login -> oauth2Login.loginPage("/login")
                                .failureUrl("/login?error")
                                .userInfoEndpoint(userInfoEndpoint ->
                                        userInfoEndpoint
                                                .userService(oAuthService)
                                )
                                .successHandler(
                                        (request, response, authentication) -> {
                                            var oidcUser = (DefaultOidcUser)authentication.getPrincipal();
                                            userService.saveOauthUser(oidcUser.getEmail(), oidcUser.getName(), oidcUser.getFullName());
                                            response.sendRedirect("/");
                                        }
                                )
                                .permitAll()
                ).rememberMe(rememberMe -> rememberMe.key("uniqueAndSecret")
                        .tokenValiditySeconds(86400)
                        .userDetailsService(userDetailsService())
                )
                .exceptionHandling(exceptionHandling ->
                        exceptionHandling.accessDeniedPage("/403"))
                .build();
    }
}

