package com.example.fms.configuration;

//@Configuration
//public class SecurityConfig {
//    @Bean
//    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
//        http
//            .csrf(AbstractHttpConfigurer::disable)
//            .authorizeHttpRequests(authRequest -> authRequest
//                    .requestMatchers(
//                            "/api/schemes",
//                            "/api/applicants/add",
//                            "/api/schemes/eligible/**")
//                    .hasRole("ADMIN") // Only ADMINs can access these specific endpoints
//                    .requestMatchers("/api/applicants").permitAll() // Anyone can access this endpoint
//                    .anyRequest().permitAll()
//            )
//            .httpBasic(Customizer.withDefaults()); // Basic authentication for ADMIN actions
//
//        return http.build();local
//    }
//
//    @Bean
//    public UserDetailsService userDetailsService() {
//        UserDetails admin = User.withUsername("admin")
//                .password(passwordEncoder().encode("admin123"))
//                .roles("ADMIN")
//                .build();
//
//        UserDetails user = User.withUsername("user")
//                .password(passwordEncoder().encode("user123"))
//                .roles("USER")
//                .build();
//
//        return new InMemoryUserDetailsManager(admin, user);
//    }
//
//    @Bean
//    public PasswordEncoder passwordEncoder() {
//        return new BCryptPasswordEncoder();
//    }
//}
