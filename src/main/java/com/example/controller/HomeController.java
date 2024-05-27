package com.example.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.security.Principal;

@Controller
public class HomeController {

    /**
     * Обработка GET запроса на "/login" (страница входа)
     *
     * @return Логическое имя представления "login.html"
     */
    @GetMapping("/login")
    public String login() {
        return "login";
    }

    /**
     * Обработка GET запроса на "/" (главная страница)
     *
     * @param model Модель Spring MVC для передачи данных в представление
     * @param principal Объект Principal, содержащий информацию о текущем пользователе
     *                  (доступен благодаря Spring Security)
     * @return Логическое имя представления "index.html"
     */
    @GetMapping("/")
    public String home(Model model, Principal principal) {
        String username = principal.getName();
        model.addAttribute("username", username);
        return "index";
    }
}
