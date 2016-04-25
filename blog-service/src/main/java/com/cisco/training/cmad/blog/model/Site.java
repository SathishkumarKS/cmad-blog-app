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

    @Getter @Setter
    private String subDomain;

    @Embedded
    private Map<ObjectId, Department> departments;

    public Site() {
    }

    public Site(String name, String subDomain) {
        this.id = new ObjectId();
        this.name = name;
        this.subDomain = subDomain;
        this.departments = new HashMap<>();
    }

    public Site addDepartment(Department department) {
        if(! this.departments.containsValue(department)) {
            this.departments.put(department.getId(), department);
        }
        return this;
    }

    public Collection<Department> getDepartments() {
        return this.departments.values();
    }

    public Department getDepartment(ObjectId departmentId) {
        return this.departments.get(departmentId);
    }

}
