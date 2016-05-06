package ua.netcracker.model.service.impl;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ua.netcracker.model.dao.UserDAO;
import ua.netcracker.model.entity.Candidate;
import ua.netcracker.model.entity.Role;
import ua.netcracker.model.entity.Status;
import ua.netcracker.model.entity.User;
import ua.netcracker.model.service.CandidateService;
import ua.netcracker.model.service.RegistrationService;
import ua.netcracker.model.utils.regex.EmailValidator;
import ua.netcracker.model.utils.regex.NameValidator;
import ua.netcracker.model.utils.regex.PasswordValidator;

import java.security.MessageDigest;
import java.util.ArrayList;
import java.util.Arrays;

@Service
public class RegistrationServiceImp implements RegistrationService {

    private static final Logger LOGGER = Logger.getLogger(RegistrationServiceImp.class);
    @Autowired
    private UserDAO userDao;

    @Autowired
    private CandidateService candidateService;

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
            if (userDao.insert(user)) {
                Candidate candidate = new Candidate();
                candidate.setUserID(userDao.findByEmail(email).getId());
                candidate.setStatusID(Status.NEW.getId());
                candidate.setCourseID(1);
                return candidateService.saveCandidate(candidate);
            }
        }
        return false;
    }

    @Override
    public boolean isFreeEmail(String email) {
        return userDao.findByEmail(email) == null;
    }

}
