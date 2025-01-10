package com.innovatrix.ahaar.config;


import com.innovatrix.ahaar.service.ApplicationUserDetailsService;
import com.innovatrix.ahaar.service.JWTService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
public class SecurityConfig {
    @Autowired
    private UserDetailsService userDetailsService;

    private JwtFilter jwtFilter;

    /**
     * Sets the JWT filter for authentication and authorization.
     *
     * @param jwtFilter The JWT filter to be used in the security configuration
     * @see JwtFilter
     */
    @Autowired
    public void setJwtFilter(JwtFilter jwtFilter) {
        this.jwtFilter = jwtFilter;
    }

    /**
     * Creates and returns a BCryptPasswordEncoder bean for secure password hashing.
     *
     * @return A BCryptPasswordEncoder instance configured with default strength
     * @see BCryptPasswordEncoder
     */
    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }


    /**
     * Constructs a SecurityConfig instance with the specified user details service.
     *
     * @param userDetailsService the service responsible for loading user-specific data during authentication
     */
    @Autowired
    public SecurityConfig(ApplicationUserDetailsService userDetailsService) {
        this.userDetailsService = userDetailsService;
    }

    /**
     * Configures the security filter chain for HTTP requests in the application.
     *
     * This method sets up a comprehensive security configuration that:
     * - Disables CSRF protection
     * - Permits unrestricted access to login, signup, and token refresh endpoints
     * - Requires authentication for all other requests
     * - Enables basic HTTP authentication
     * - Configures stateless session management
     * - Adds a custom JWT filter before the standard authentication filter
     *
     * @param http The {@link HttpSecurity} configuration to be customized
     * @return A fully configured {@link SecurityFilterChain}
     * @throws Exception if an error occurs during security configuration
     */
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(authorize -> authorize
                        .requestMatchers("/user/login", "/user/signup", "/user/refreshToken").permitAll()
                        .anyRequest().authenticated()
                )
                .httpBasic(Customizer.withDefaults())
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);
//                .formLogin(formLogin -> formLogin.loginPage("/login").defaultSuccessUrl("/home", true)); // Customize as needed
        // Enables default form login

        return http.build();
    }

    /**
     * Creates and configures a DaoAuthenticationProvider for user authentication.
     *
     * This method sets up an authentication provider that uses BCrypt password encoding
     * and the application's custom UserDetailsService for validating user credentials.
     *
     * @return A configured DaoAuthenticationProvider used for authenticating users
     */
    @Bean
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setPasswordEncoder(bCryptPasswordEncoder());
        authProvider.setUserDetailsService(userDetailsService);
        return authProvider;
    }

    /**
     * Creates and returns an AuthenticationManager from the provided AuthenticationConfiguration.
     *
     * @param config The AuthenticationConfiguration used to obtain the AuthenticationManager
     * @return An AuthenticationManager for handling authentication processes
     * @throws Exception If there is an error retrieving the AuthenticationManager
     */
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }
}
