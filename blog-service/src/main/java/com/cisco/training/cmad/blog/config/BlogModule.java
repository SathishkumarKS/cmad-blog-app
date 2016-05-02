package com.cisco.training.cmad.blog.config;

import com.cisco.training.cmad.blog.dao.*;
import com.cisco.training.cmad.blog.mapper.CompanyMapper;
import com.cisco.training.cmad.blog.mapper.UserMapper;
import com.cisco.training.cmad.blog.service.*;
import com.google.inject.AbstractModule;
import com.google.inject.Provides;
import com.mongodb.MongoClient;
import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.Morphia;

/**
 * Created by satkuppu on 26/04/16.
 */
public class BlogModule extends AbstractModule {
    @Override
    protected void configure() {
        bind(CompanyService.class).to(CompanyServiceImpl.class);
        bind(UserService.class).to(UserServiceImpl.class);
        bind(BlogService.class).to(BlogServiceImpl.class);

        bind(CompanyDAO.class).to(CompanyDAOImpl.class);
        bind(UserDAO.class).to(UserDAOImpl.class);
        bind(BlogDAO.class).to(BlogDAOImpl.class);

        bind(CompanyMapper.class);
        bind(UserMapper.class);
    }

    @Provides
    Datastore getDataStore() {
        MongoClient mongoClient = new MongoClient("172.16.133.132", 27017);
        //create a new morphia instance
        Datastore datastore = new Morphia().createDatastore(mongoClient, "blogs");
        datastore.ensureIndexes();
        return datastore;
    }
}
