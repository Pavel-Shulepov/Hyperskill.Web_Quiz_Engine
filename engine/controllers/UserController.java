package engine.controllers;

import engine.domain.User;
import engine.services.UserService;
import org.apache.commons.validator.routines.EmailValidator;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("/api")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @RequestMapping(path = "/register", method = RequestMethod.POST)
    public ResponseEntity<?> register(@RequestBody final User user) {
        System.out.println(user.getEmail() + user.getPassword());
        if (!isValidEmail(user.getEmail()))
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Не верный email");
        userService.createUser(user);
        return ResponseEntity.ok().build();
    }

    private static boolean isValidEmail(String email) {
        EmailValidator validator = EmailValidator.getInstance();
        return validator.isValid(email);
    }

}
