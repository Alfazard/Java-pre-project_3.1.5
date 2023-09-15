package ru.kata.spring.boot_security.demo.configs;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import ru.kata.spring.boot_security.demo.service.UserServiceImpl;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig  {
//    extends WebSecurityConfigurerAdapter
    private final SuccessUserHandler successUserHandler;
    private final UserServiceImpl userService;

    public WebSecurityConfig(SuccessUserHandler successUserHandler, UserServiceImpl userService) {
        this.successUserHandler = successUserHandler;
        this.userService = userService;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                .authorizeRequests((requests) -> requests
                .regexMatchers("/login", "/").permitAll()
                .regexMatchers("/user/*").hasAnyRole("USER","ADMIN")
                .regexMatchers("/admin/*").hasRole("ADMIN")
                .anyRequest().authenticated()
                )
                .formLogin((form) -> form
                .successHandler(successUserHandler)
                .loginPage("/")
                .loginProcessingUrl("/login")
                .usernameParameter("Email address")
                .passwordParameter("Password")
                .permitAll()
                )
                .logout()
                .logoutUrl("/logout")
                .logoutSuccessUrl("/");
        return http.build();
    }

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(12);
    }

    @Bean
    public DaoAuthenticationProvider daoAuthenticationProvider() {
        DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
        authenticationProvider.setPasswordEncoder(passwordEncoder());
        authenticationProvider.setUserDetailsService(userService);

        return authenticationProvider;
    }
}