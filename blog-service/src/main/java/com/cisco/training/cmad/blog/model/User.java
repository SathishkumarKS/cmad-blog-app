package com.cisco.training.cmad.blog.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.bson.types.ObjectId;
import org.mongodb.morphia.annotations.Embedded;
import org.mongodb.morphia.annotations.Entity;
import org.mongodb.morphia.annotations.Id;

/**
 * Created by satkuppu on 4/23/16.
 */
@Entity(value = "users", noClassnameStored=true)
@ToString
public class User {
    @Id @Getter
    private ObjectId id;

    @Getter
    private String userName;

    @Getter
    private String password;

    @Getter
    private String email;

    @Getter @Setter
    private String firstName;

    @Getter @Setter
    private String lastName;

    @Embedded
    private UserDepartment department;

    public User() {

    }

    public User(String userName, String password, String email) {
        this.userName = userName;
        this.password = password;
        this.email = email;
    }

    public void worksAtDepartment(UserDepartment userDepartment) {
        this.department = userDepartment;
    }
}

