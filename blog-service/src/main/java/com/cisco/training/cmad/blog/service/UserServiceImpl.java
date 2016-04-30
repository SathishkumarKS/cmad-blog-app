package com.cisco.training.cmad.blog.service;

import com.cisco.training.cmad.blog.dao.UserDAO;
import com.cisco.training.cmad.blog.dto.UserAuthDTO;
import com.cisco.training.cmad.blog.dto.UserDTO;
import com.cisco.training.cmad.blog.dto.UserRegistrationDTO;
import com.cisco.training.cmad.blog.exception.DataNotFound;
import com.cisco.training.cmad.blog.exception.UserAlreadyExists;
import com.cisco.training.cmad.blog.mapper.UserMapper;
import com.cisco.training.cmad.blog.model.User;
import com.cisco.training.cmad.blog.model.UserDepartment;
import com.google.inject.Inject;
import com.mongodb.DuplicateKeyException;
import org.mindrot.jbcrypt.BCrypt;
import org.mongodb.morphia.Key;

/**
 * Created by satkuppu on 25/04/16.
 */
public class UserServiceImpl implements UserService {

    private UserDAO userDAO;
    private UserMapper userMapper;

    @Inject
    public UserServiceImpl(UserDAO userDAO, UserMapper userMapper) {
        this.userDAO = userDAO;
        this.userMapper = userMapper;
    }

    @Override
    public String registerUser(UserRegistrationDTO userRegistration) throws UserAlreadyExists {
        User user = userMapper.toUser(userRegistration);
        try {
            Key<User> userId = userDAO.save(user);
            return userId.getId().toString();
        } catch (DuplicateKeyException dke) {
            throw new UserAlreadyExists("User with userName " + userRegistration.getUserName() + " already exists");
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

    @Override
    public UserDTO getUser(String userName) {
        User user = userDAO.getByUserName(userName);
        if(user == null) {
            throw new DataNotFound("User with userName " + userName + " doesn't exist");
        }
        return userMapper.toUserDTO(user);
    }
}
