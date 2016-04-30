package com.cisco.training.cmad.blog.service;

import com.cisco.training.cmad.blog.dao.UserDAO;
import com.cisco.training.cmad.blog.dto.UserAuthDTO;
import com.cisco.training.cmad.blog.dto.UserRegistrationDTO;
import com.cisco.training.cmad.blog.exception.UserAlreadyExists;
import com.cisco.training.cmad.blog.model.User;
import com.cisco.training.cmad.blog.model.UserDepartment;
import com.google.inject.Inject;
import com.mongodb.DuplicateKeyException;
import org.bson.types.ObjectId;
import org.mindrot.jbcrypt.BCrypt;
import org.mongodb.morphia.Key;

/**
 * Created by satkuppu on 25/04/16.
 */
public class UserServiceImpl implements UserService {

    private UserDAO userDAO;

    @Inject
    public UserServiceImpl(UserDAO userDAO) {
        this.userDAO = userDAO;
    }

    @Override
    public String registerUser(UserRegistrationDTO userRegistration) throws UserAlreadyExists {
        final String userName = userRegistration.getUserName();
        String hashedPassword = BCrypt.hashpw(userRegistration.getPassword(), BCrypt.gensalt());

        User user = new User(userName, hashedPassword, userRegistration.getEmail());
        user.setFirstName(userRegistration.getFirst());
        user.setLastName(userRegistration.getLast());

        UserDepartment userDepartment = new UserDepartment(
                new ObjectId(userRegistration.getCompanyId()),
                new ObjectId(userRegistration.getSiteId()),
                new ObjectId(userRegistration.getDeptId()));
        userDepartment.setCompanyName(userRegistration.getCompanyName());
        userDepartment.setSiteName(userRegistration.getSiteName());
        userDepartment.setDepartmentName(userRegistration.getDeptName());

        user.worksAtDepartment(userDepartment);
        try {
            Key<User> userId = userDAO.save(user);
            return userId.getId().toString();
        } catch (DuplicateKeyException dke) {
            throw new UserAlreadyExists("User with userName " + userName + " already exists");
        }
    }

    @Override
    public Boolean authenticateUser(UserAuthDTO userAuthDTO) {
        User user = userDAO.getByUserName(userAuthDTO.getUserName());
        if (BCrypt.checkpw(userAuthDTO.getPassword(), user.getPassword()))
            return Boolean.TRUE;
        else
            return Boolean.FALSE;
    }
}
