package ua.netcracker.model.service.impl;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import ua.netcracker.model.dao.UserDAO;
import ua.netcracker.model.entity.Role;
import ua.netcracker.model.entity.User;
import ua.netcracker.model.securiry.UserAuthenticationDetails;
import ua.netcracker.model.service.UserService;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Collection;
import java.util.List;
import java.util.UUID;

/**
 * Created by Legion on 05.05.2016.
 * Modified by Bersik
 */
@Service("user service")
public class UserServiceImpl implements UserService {
    private static final Logger LOGGER = Logger.getLogger(UserServiceImpl.class);

    @Autowired
    private UserDAO userDao;

    @Value("${userPhotoFolder}")
    private String absolutePath;

    public List<User> getAllPersonal(Integer id) {
        return userDao.getAllPersonalById(id);
    }


    @Override
    public boolean saveUserPhoto(MultipartFile image, int x, int y, int width, int height) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (!(auth instanceof AnonymousAuthenticationToken)) {
            UserAuthenticationDetails userDetails =
                    (UserAuthenticationDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

            User user = userDao.findByEmail(userDetails.getUsername());

            if (!new File(absolutePath + user.getImage()).delete()) {
                LOGGER.info("Cannot remove file " + absolutePath + user.getImage());
            }

            String fileName;
            String fullPath;
            File file;
            do {
                fileName = generateUUID() + ".jpg";
                fullPath = absolutePath + fileName;
                file = new File(fullPath);
            } while (file.exists());
            try {
                saveImage(image, x, y, width, height, file);
                user.setImage(fileName);
                userDao.update(user);
                userDetails.getUser().setImage(fileName);
                LOGGER.info("Upload user photo with location =" + fullPath);
                return true;
            } catch (IOException e) {
                LOGGER.error("Error upload user photo with location " + fullPath);
            }
        }
        LOGGER.error("Error load UserAuthenticationDetails");
        return false;
    }

    private void saveImage(MultipartFile imgFile, int x, int y, int width, int height, File file) throws IOException {
        InputStream in = new ByteArrayInputStream(imgFile.getBytes());
        BufferedImage image = ImageIO.read(in);
        image = image.getSubimage(x, y, width, height);

        ImageIO.write(image, "jpg", file);
    }

    @Override
    public boolean addUserRole(User user, Role role){
        userDao.removeUserRoles(user.getId());
        Collection<Role> roles = user.getRoles();
        roles.add(role);
        user.setRoles((List<Role>) roles);
        return userDao.insertUserRoles(user);
    }

    @Override
    public User get(int id) {
        return userDao.find(id);
    }

    @Override
    public User getAuthorizedUser() {
        int userId = 0;
        try {
            Authentication auth = SecurityContextHolder.getContext().getAuthentication();
            if (!(auth instanceof AnonymousAuthenticationToken)) {
                UserAuthenticationDetails userDetails =
                        (UserAuthenticationDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
                userId = userDetails.getUserId();
            }
            return userDao.find(userId);
        }catch (Exception e){
            LOGGER.error("Error : " + e);
        }
        return null;
    }

    @Override
    public Integer getAllWorkers() {
        return userDao.findAllWorkers();
    }

    private String generateUUID() {
        UUID id = UUID.randomUUID();
        return String.valueOf(id);
    }
}
