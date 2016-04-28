package ua.netcracker.hr_system.model.service.serviceImpl;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ua.netcracker.hr_system.model.dao.daoImpl.CandidateDaoImpl;
import ua.netcracker.hr_system.model.dao.daoImpl.UserDaoImpl;
import ua.netcracker.hr_system.model.entity.Role;
import ua.netcracker.hr_system.model.entity.User;

import ua.netcracker.hr_system.model.service.serviceInterface.RegistrationService;
import ua.netcracker.hr_system.model.utils.regex.EmailValidator;
import ua.netcracker.hr_system.model.utils.regex.NameValidator;
import ua.netcracker.hr_system.model.utils.regex.PasswordValidator;

import java.security.MessageDigest;
import java.util.ArrayList;
import java.util.Arrays;

@Service("registrationService")
public class RegistrationServiceImp implements RegistrationService {

    private static final Logger LOGGER = Logger.getLogger(CustomUserDetailsServiceImpl.class);

    @Autowired(required = false)
    UserDaoImpl userDao;

    private static String sha256Password(String password) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");

            md.update(password.getBytes("UTF-8"));
            byte[] digest = md.digest();
            return String.format("%064x", new java.math.BigInteger(1, digest));
        } catch (Exception e) {
            LOGGER.error(e);
        }
        return null;
    }

    @Override
    public boolean registrationStudent(String email, String name, String surname, String patronymic, String password) {
        EmailValidator ev = new EmailValidator();
        NameValidator nv = new NameValidator();
        PasswordValidator pv = new PasswordValidator();

        if (ev.validate(email) && nv.validate(name) && nv.validate(surname)
                && nv.validate(patronymic) && pv.validate(password)) {

         User user = new User(email, sha256Password(password), name, surname, patronymic,
                    new ArrayList<>(Arrays.asList(Role.STUDENT)));
          return userDao.insert(user);
        }
        return false;
    }

    @Override
    public boolean isFreeEmail(String email) {
        return userDao.findByEmail(email) == null;
    }

}
