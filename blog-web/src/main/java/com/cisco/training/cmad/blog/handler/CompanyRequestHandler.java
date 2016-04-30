package com.cisco.training.cmad.blog.handler;

import com.cisco.training.cmad.blog.dto.DepartmentDTO;
import com.cisco.training.cmad.blog.dto.SiteDTO;
import com.cisco.training.cmad.blog.exception.DataNotFound;
import com.cisco.training.cmad.blog.service.CompanyService;
import com.google.inject.Inject;
import io.netty.handler.codec.http.HttpResponseStatus;
import io.vertx.core.http.HttpHeaders;
import io.vertx.core.json.Json;
import io.vertx.ext.web.RoutingContext;

import javax.ws.rs.core.MediaType;
import java.util.List;

/**
 * Created by satkuppu on 30/04/16.
 */
public class CompanyRequestHandler {

    @Inject
    CompanyService companyService;

    public void getAllCompanies(RoutingContext routingContext) {
        routingContext.response()
                .putHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON)
                .end(Json.encodePrettily(companyService.getAllCompanies()));
    }

    public void getSites(RoutingContext routingContext) {
        String companyId = routingContext.request().getParam("companyId");
        routingContext.vertx().executeBlocking(future -> {
            List<SiteDTO> sites = companyService.getSites(companyId);
            future.complete(sites);
        }, res -> {
            if(res.succeeded()) {
                routingContext.response()
                        .putHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON)
                        .setStatusCode(HttpResponseStatus.OK.code())
                        .end(Json.encodePrettily(res.result()));
            } else {
                ExceptionHandler.handleException(res, routingContext);
            }
        });
    }

    public void getDepartments(RoutingContext routingContext) {
        String companyId = routingContext.request().getParam("companyId");
        String siteId = routingContext.request().getParam("siteId");
        routingContext.vertx().executeBlocking(future -> {
            try {
                List<DepartmentDTO> departments = companyService.getDepartments(companyId, siteId);
                future.complete(departments);
            } catch(DataNotFound e) {
                future.fail(e);
            }
        }, res -> {
            if(res.succeeded()) {
                routingContext.response()
                        .putHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON)
                        .setStatusCode(HttpResponseStatus.OK.code())
                        .end(Json.encodePrettily(res.result()));
            } else {
                ExceptionHandler.handleException(res, routingContext);
            }
        });
    }

}