package com.cisco.training.cmad.blog.web.module;

import com.cisco.training.cmad.blog.config.BlogModule;
import com.cisco.training.cmad.blog.handler.CompanyRequestHandler;
import com.cisco.training.cmad.blog.handler.UserRequestHandler;
import com.google.inject.AbstractModule;

/**
 * Created by satkuppu on 30/04/16.
 */
public class BlogWebModule extends AbstractModule {

    @Override
    protected void configure() {
        install(new BlogModule());
        bind(CompanyRequestHandler.class);
        bind(UserRequestHandler.class);
    }
}
