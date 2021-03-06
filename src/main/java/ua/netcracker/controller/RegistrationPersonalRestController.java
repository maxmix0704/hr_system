package ua.netcracker.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ua.netcracker.model.dao.UserDAO;
import ua.netcracker.model.entity.Role;
import ua.netcracker.model.entity.User;
import ua.netcracker.model.securiry.SHA256PasswordEncoder;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created by Серый on 27.04.2016.
 */
@RestController
public class RegistrationPersonalRestController {

    private User user;

    @Autowired
    private void setUser(User user) {
        this.user = user;
    }

    private UserDAO userDAO;

    @Autowired
    private void setUserDao(UserDAO userDAO) {
        this.userDAO = userDAO;
    }

    @Autowired
    private SHA256PasswordEncoder passwordEncoder;

    public static Role getRoleByStr(String role) {
        return Role.valueOf(role);
    }

    @RequestMapping(value = "/new_personal", method = RequestMethod.POST)
    public ResponseEntity<User> setNewPersonal(
            @RequestParam String Role_Id,
            @RequestParam String name_peronal,
            @RequestParam String sername_peronal,
            @RequestParam String email_peronal,
            @RequestParam String password_peronal,
            @RequestParam String patronymic_peronal
    ) {


        user.setName(name_peronal);
        user.setSurname(sername_peronal);
        user.setPatronymic(patronymic_peronal);
        user.setPassword(passwordEncoder.encode(password_peronal));
        user.setEmail(email_peronal);
        user.setRoles(new ArrayList<>(Arrays.asList(getRoleByStr(Role_Id))));

        userDAO.insert(user);


        return ResponseEntity.ok(user);
    }

}