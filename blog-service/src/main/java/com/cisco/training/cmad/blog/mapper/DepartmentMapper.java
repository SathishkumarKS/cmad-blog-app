package com.cisco.training.cmad.blog.mapper;

import com.cisco.training.cmad.blog.dto.DepartmentDTO;
import com.cisco.training.cmad.blog.model.Company;
import com.cisco.training.cmad.blog.model.Department;
import com.cisco.training.cmad.blog.model.Site;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;

/**
 * Created by satkuppu on 4/25/16.
 */
//@Mapper
public interface DepartmentMapper {

    DepartmentMapper INSTANCE = Mappers.getMapper( DepartmentMapper.class );

    @Mappings({
            @Mapping(source = "company.id.toString", target = "companyId"),
            @Mapping(source = "site.id.toString", target = "siteId"),
            @Mapping(source = "department.id.toString", target = "departmentId"),
            @Mapping(source = "department.name", target = "departmentName")
    })
    DepartmentDTO companySiteAndDepartmentToDepartmentDTO(Company company, Site site, Department department);
}
