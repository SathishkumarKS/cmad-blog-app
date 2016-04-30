package com.cisco.training.cmad.blog.model;

import lombok.*;
import org.bson.types.ObjectId;
import org.mongodb.morphia.annotations.Embedded;

import java.util.*;

/**
 * Created by satkuppu on 4/23/16.
 */
@EqualsAndHashCode(exclude={"id", "departments"})
@ToString
public class Site {

    @Getter
    private ObjectId id;

    @Getter @Setter @NonNull
    private String name;

    @Embedded
    private Map<ObjectId, Department> departments;

    public Site() {
    }

    public Site(String name) {
        this.id = new ObjectId();
        this.name = name;
        this.departments = new HashMap<>();
    }

    public Site addDepartment(Department department) {
        if(! this.departments.containsValue(department)) {
            this.departments.put(department.getId(), department);
        }
        return this;
    }

    public Optional<Map<ObjectId, Department>> getDepartments() {
        return Optional.ofNullable(this.departments);
    }

    public Optional<Department> getDepartment(ObjectId departmentId) {
        return Optional.ofNullable(this.departments.get(departmentId));
    }

}
