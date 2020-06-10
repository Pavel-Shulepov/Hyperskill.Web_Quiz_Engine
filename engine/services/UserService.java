package engine.services;

import engine.domain.User;
import engine.perositories.UserRepo;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import org.apache.commons.validator.routines.EmailValidator;

import java.awt.image.BufferedImageOp;
import java.util.Optional;

@Service
public class UserService {

    private final UserRepo userRepo;

    public UserService(UserRepo userRepo) {
        this.userRepo = userRepo;
    }

    public void createUser(User user) {
        if (user.getPassword().length() < 5)
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Парооль должен содежать не менее 5 символов");
        if (!isValidEmail(user.getEmail()))
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Не верный email");
        System.out.println(isValidEmail(user.getEmail()));
        user.setPassword(passwordEncoder().encode(user.getPassword()));
        if (userRepo.findByEmail(user.getEmail()).isPresent()) throw  new ResponseStatusException(HttpStatus.BAD_REQUEST, "Email занят");
        userRepo.save(user);
    }

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    private static boolean isValidEmail(String email) {
        EmailValidator validator = EmailValidator.getInstance();
        return validator.isValid(email);
    }

    public Optional<User> findByEmail(String email) {
        return userRepo.findByEmail(email);
    }
}
