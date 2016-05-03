package com.cisco.training.cmad.blog.model;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.bson.types.ObjectId;

/**
 * Created by satkuppu on 4/23/16.
 */
@EqualsAndHashCode(exclude={"id"})
@ToString
public class Department {

    @Getter
    private ObjectId id;

    @Getter @Setter
    private String name;

    public Department() {
    }

    public Department(String departmentName) {
        this.id = new ObjectId();
        this.name = departmentName;
    }
}
