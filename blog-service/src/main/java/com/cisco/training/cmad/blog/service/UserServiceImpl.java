package com.cisco.training.cmad.blog.service;

import com.cisco.training.cmad.blog.dao.UserDAO;
import com.cisco.training.cmad.blog.dto.UserAuthDTO;
import com.cisco.training.cmad.blog.dto.UserDTO;
import com.cisco.training.cmad.blog.dto.UserRegistrationDTO;
import com.cisco.training.cmad.blog.exception.DataNotFound;
import com.cisco.training.cmad.blog.exception.UserAlreadyExists;
import com.cisco.training.cmad.blog.mapper.UserMapper;
import com.cisco.training.cmad.blog.model.User;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.mongodb.DuplicateKeyException;
import org.mindrot.jbcrypt.BCrypt;
import org.mongodb.morphia.Key;

import java.util.Optional;
import java.util.jar.Pack200;

/**
 * Created by satkuppu on 25/04/16.
 */
@Singleton
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
            throw new UserAlreadyExists("User with userName " + userRegistration.getUserName() + " already exists", dke);
        }
    }

    @Override
    public Boolean authenticateUser(UserAuthDTO userAuthDTO) {
        Optional<User> user = userDAO.getByUserName(userAuthDTO.getUserName());

        if(user.isPresent()) {
            if (BCrypt.checkpw(userAuthDTO.getPassword(), user.get().getPassword()))
                return Boolean.TRUE;
            else
                return Boolean.FALSE;
        } else {
            return Boolean.FALSE;
        }
    }

    @Override
    public UserDTO getUser(String userName) {
        Optional<User> user = userDAO.getByUserName(userName);
        if(user.isPresent()) {
            return userMapper.toUserDTO(user.get());
        }
        throw new DataNotFound("User with userName " + userName + " doesn't exist");
    }
}
