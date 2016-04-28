package com.cisco.training.cmad.blog.config;

import com.cisco.training.cmad.blog.dao.CompanyDAO;
import com.cisco.training.cmad.blog.dao.CompanyDAOImpl;
import com.cisco.training.cmad.blog.dao.UserDAO;
import com.cisco.training.cmad.blog.dao.UserDAOImpl;
import com.cisco.training.cmad.blog.service.CompanyService;
import com.cisco.training.cmad.blog.service.CompanyServiceImpl;
import com.cisco.training.cmad.blog.service.UserService;
import com.cisco.training.cmad.blog.service.UserServiceImpl;
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
        bind(CompanyDAO.class).to(CompanyDAOImpl.class);
        bind(UserDAO.class).to(UserDAOImpl.class);
    }

    @Provides
    Datastore getDataStore() {
        MongoClient mongoClient = new MongoClient("173.36.55.107", 27017);
        //create a new morphia instance
        Datastore datastore = new Morphia().createDatastore(mongoClient, "blogs");
        datastore.ensureIndexes();
        return datastore;
    }
}
