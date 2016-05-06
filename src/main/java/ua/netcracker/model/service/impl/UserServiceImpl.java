package ua.netcracker.model.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ua.netcracker.model.dao.UserDAO;
import ua.netcracker.model.entity.User;

import java.util.List;

/**
 * Created by Legion on 05.05.2016.
 */
@Service("user service")
public class UserServiceImpl {

    @Autowired
    private UserDAO userDao;

    public List<User> getAllPersonal(Integer id){
        return userDao.getAllPersonalById(id);
    }
}
