package com.example.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration {

    /**
     * Бин для создания объекта BCryptPasswordEncoder, который используется для хэширования паролей
     *
     * @return Объект BCryptPasswordEncoder
     */
    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    /**
     * Бин для создания цепочки фильтров безопасности Spring Security (SecurityFilterChain)
     *
     * @param http HttpSecurity объект для конфигурирования безопасности
     * @return SecurityFilterChain объект, представляющий сконфигурированную цепочку фильтров
     * @throws Exception Ошибки при конфигурировании безопасности
     */
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests((requests) -> requests
                        // Разрешить доступ к странице регистрации для всех пользователей
                        .requestMatchers("/registration").permitAll()
                        // Требовать авторизацию для всех остальных запросов
                        .anyRequest().authenticated()
                )
                .formLogin((form) -> form
                        // Установить страницу входа по адресу "/login" и разрешить доступ к ней всем пользователям
                        .loginPage("/login")
                        .permitAll()
                )
                .logout((logout) -> logout
                        // Установить URL выхода из системы на "/logout"
                        .logoutUrl("/logout")
                        // Перенаправлять на главную страницу после успешного выхода
                        .logoutSuccessUrl("/")
                        // Инвалидировать HttpSession после выхода
                        .invalidateHttpSession(true));

        return http.build();
    }
}
