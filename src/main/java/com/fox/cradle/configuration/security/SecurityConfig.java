package com.fox.cradle.configuration.security;

import org.springframework.context.annotation.Configuration;


@Configuration
public class SecurityConfig
{
/*
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http)  throws Exception
    {
        http.authorizeRequests().anyRequest().permitAll();
        return http.build();
    }
*/

/*
    @Bean
    public WebSecurityCustomizer webSecurityCustomizer() {
        return (web) -> web.ignoring()
                // Spring Security should completely ignore URLs starting with /resources/
                .requestMatchers("/h2-console/**");
    }
*/

/*
    @Bean
    public UserDetailsService userDetailsService() {
        UserDetails user = User.withDefaultPasswordEncoder()
                .username("user")
                .password("password")
                .roles("USER")
                .build();
        UserDetails admin = User.withDefaultPasswordEncoder()
                .username("admin")
                .password("password")
                .roles("ADMIN", "USER")
                .build();
        return new InMemoryUserDetailsManager(user, admin);
    }
 */
}

