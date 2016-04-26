package com.cisco.training.cmad.blog.service;

import com.cisco.training.cmad.blog.dao.UserDAO;
import com.cisco.training.cmad.blog.dto.UserRegistrationDTO;
import com.cisco.training.cmad.blog.model.User;
import com.cisco.training.cmad.blog.model.UserDepartment;
import org.bson.types.ObjectId;
import org.mongodb.morphia.Key;

/**
 * Created by satkuppu on 25/04/16.
 */
public class UserServiceImpl implements UserService {

    private UserDAO userDAO;

    public UserServiceImpl(UserDAO userDAO) {
        this.userDAO = userDAO;
    }

    @Override
    public String registerUser(UserRegistrationDTO userRegistration) {
        User user = new User(userRegistration.getUserName(), userRegistration.getPassword(), userRegistration.getEmail());
        user.setFirstName(userRegistration.getFirst());
        user.setLastName(userRegistration.getLast());
        UserDepartment userDepartment = new UserDepartment(
                new ObjectId(userRegistration.getCompanyId()),
                new ObjectId(userRegistration.getSiteId()),
                new ObjectId(userRegistration.getDeptId()));

        user.worksAtDepartment(userDepartment);
        Key<User> userId = userDAO.save(user);
        return userId.toString();
    }
}
