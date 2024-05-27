package com.example.service.impl;


import com.example.dto.UserRegistrationDto;
import com.example.model.Role;
import com.example.model.User;
import com.example.repository.UserRepository;
import com.example.service.interfaces.UserService;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {


    

    /**
     * Репозиторий для работы с сущностью User
     */
    private UserRepository userRepository;

    /**
     * Bean для хэширования паролей
     */
    private BCryptPasswordEncoder passwordEncoder;

    public UserServiceImpl(UserRepository userRepository, BCryptPasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    /**
     * Сохранение нового пользователя на основе данных регистрации
     *
     * @param registrationDto DTO-объект, содержащий данные регистрации (имя, фамилия, email, пароль)
     * @return Сохраненный пользователь (объект User)
     */
    @Override
    public User save(UserRegistrationDto registrationDto) {
        User user = new User(
                registrationDto.getFirstName(),
                registrationDto.getLastName(),
                registrationDto.getEmail(),
                passwordEncoder.encode(registrationDto.getPassword()),
                Arrays.asList(new Role("ROLE_ADMIN")) // Роль по умолчанию - ROLE_ADMIN
        );

        return userRepository.save(user);
    }

    /**
     * Загрузка пользователя по email для Spring Security
     *
     * @param username Email пользователя
     * @return UserDetails объект, представляющий информацию о пользователе для Spring Security
     * @throws UsernameNotFoundException Ошибка, возникающая если пользователь с таким email не найден
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByEmail(username);
        if (user == null) {
            throw new UsernameNotFoundException("Wrong email or password.");
        }

        return new org.springframework.security.core.userdetails.User(
                user.getEmail(),
                user.getPassword(),
                mapRolesToAuthorities(user.getRoles())
        );
    }

    /**
     * Преобразование коллекции ролей пользователя в коллекцию GrantedAuthority для Spring Security
     *
     * @param roles Коллекция объектов Role, представляющая роли пользователя
     * @return Коллекция объектов GrantedAuthority, необходимая для Spring Security
     */
    private Collection<? extends GrantedAuthority> mapRolesToAuthorities(Collection<Role> roles) {
        return roles.stream()
                .map(role -> new SimpleGrantedAuthority(role.getName()))
                .collect(Collectors.toList());
    }

    /**
     * Получение всех пользователей из базы данных
     *
     * @return Список объектов User, представляющий всех пользователей
     */
     
    @Override
    public List<User> getAll() {
        return userRepository.findAll();
    }






}

