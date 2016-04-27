package com.cisco.training.cmad.blog.service;

import com.cisco.training.cmad.blog.dao.UserDAO;
import com.cisco.training.cmad.blog.dto.UserAuthDTO;
import com.cisco.training.cmad.blog.dto.UserRegistrationDTO;
import com.cisco.training.cmad.blog.model.User;
import com.cisco.training.cmad.blog.model.UserDepartment;
import com.google.inject.Inject;
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
    public String registerUser(UserRegistrationDTO userRegistration) {
        String hashedPassword = BCrypt.hashpw(userRegistration.getPassword(), BCrypt.gensalt());

        User user = new User(userRegistration.getUserName(), hashedPassword, userRegistration.getEmail());
        user.setFirstName(userRegistration.getFirst());
        user.setLastName(userRegistration.getLast());
        UserDepartment userDepartment = new UserDepartment(
                new ObjectId(userRegistration.getCompanyId()),
                new ObjectId(userRegistration.getSiteId()),
                new ObjectId(userRegistration.getDeptId()));

        user.worksAtDepartment(userDepartment);
        System.out.println("user = " + user);
        Key<User> userId = userDAO.save(user);
        return userId.toString();
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
