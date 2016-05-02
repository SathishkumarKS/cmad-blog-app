package com.cisco.training.cmad.blog.mapper;

import com.cisco.training.cmad.blog.dto.UserDTO;
import com.cisco.training.cmad.blog.dto.UserRegistrationDTO;
import com.cisco.training.cmad.blog.model.User;
import com.cisco.training.cmad.blog.model.UserDepartment;
import org.mindrot.jbcrypt.BCrypt;

/**
 * Created by satkuppu on 30/04/16.
 */
public class UserMapper {
    public UserDTO toUserDTO(User user) {
        UserDTO userDTO = new UserDTO();
        userDTO.setId(user.getId().toString());
        userDTO.setUserName(user.getUserName());
        userDTO.setFirst(user.getFirstName());
        userDTO.setLast(user.getLastName());
        userDTO.setEmail(user.getEmail());

        UserDepartment userDepartment = user.getDepartment();
        userDTO.setCompanyId(userDepartment.getCompanyId());
        userDTO.setCompanyName(userDepartment.getCompanyName());
        userDTO.setSiteId(userDepartment.getSiteId());
        userDTO.setSiteName(userDepartment.getSiteName());
        userDTO.setDepartmentId(userDepartment.getDepartmentId());
        userDTO.setDepartmentName(userDepartment.getDepartmentName());

        return userDTO;
    }

    public User toUser(UserRegistrationDTO userRegistration) {
        final String userName = userRegistration.getUserName();
        String hashedPassword = BCrypt.hashpw(userRegistration.getPassword(), BCrypt.gensalt());

        User user = new User(userName, hashedPassword, userRegistration.getEmail());
        user.setFirstName(userRegistration.getFirst());
        user.setLastName(userRegistration.getLast());

        UserDepartment userDepartment = new UserDepartment(
                userRegistration.getCompanyId(),
                userRegistration.getSiteId(),
                userRegistration.getDeptId());
        userDepartment.setCompanyName(userRegistration.getCompanyName());
        userDepartment.setSiteName(userRegistration.getSiteName());
        userDepartment.setDepartmentName(userRegistration.getDeptName());

        user.worksAtDepartment(userDepartment);

        return user;

    }

}
