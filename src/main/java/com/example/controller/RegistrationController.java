package com.example.controller;

import com.example.dto.UserRegistrationDto;
import com.example.service.interfaces.UserService;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/registration")
public class RegistrationController {

    /**
     * Сервис для работы с пользователями
     */
    private UserService userService;

    public RegistrationController(UserService userService) {
        this.userService = userService;
    }

    /**
     * Добавление пустого объекта UserRegistrationDto в модель под ключом "user"
     * (будет доступен в представлении для заполнения формы регистрации)
     *
     * @return Новый объект UserRegistrationDto
     */
    @ModelAttribute("user")
    public UserRegistrationDto userRegistrationDto() {
        return new UserRegistrationDto();
    }

    /**
     * Обработка GET запроса на "/registration"
     *
     * @return Логическое имя представления "registration.html"
     */
    @GetMapping
    public String showRegistrationForm() {
        return "registration"; // Ожидается, что существует шаблон "registration.html"
    }

    /**
     * Обработка POST запроса на "/registration" (отправка формы регистрации)
     *
     * @param registrationDto DTO-объект, содержащий данные регистрации (из формы)
     * @return Перенаправление на страницу регистрации с сообщением об успехе
     * или ошибке (в зависимости от результата регистрации)
     */
    @PostMapping
    public String registerUserAccount(@ModelAttribute("user") UserRegistrationDto registrationDto) {

        try {
            userService.save(registrationDto);
            return "redirect:/registration?success"; // Перенаправление с сообщением об успехе
        } catch (Exception e) {
            System.out.println(e); // Логирование ошибки 
            return "redirect:/registration?email_invalid"; // Перенаправление с сообщением об ошибке
        }
    }
}
