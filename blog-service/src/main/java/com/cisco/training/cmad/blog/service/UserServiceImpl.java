package com.cisco.training.cmad.blog.service;

import com.cisco.training.cmad.blog.dao.UserDAO;
import com.cisco.training.cmad.blog.dto.UserAuthDTO;
import com.cisco.training.cmad.blog.dto.UserRegistrationDTO;
import com.cisco.training.cmad.blog.exception.UserAlreadyExistsException;
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
    public String registerUser(UserRegistrationDTO userRegistration) throws UserAlreadyExistsException{
        final String userName = userRegistration.getUserName();
        User user = userDAO.getByUserName(userName);
        if(user == null) {
            String hashedPassword = BCrypt.hashpw(userRegistration.getPassword(), BCrypt.gensalt());

            user = new User(userName, hashedPassword, userRegistration.getEmail());
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
            Key<User> userId = userDAO.save(user);
            return userId.getId().toString();
        }
        throw new UserAlreadyExistsException("User with userName " + userName + " already exists");
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
