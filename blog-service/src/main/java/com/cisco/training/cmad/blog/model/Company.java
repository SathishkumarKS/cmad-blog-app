package com.cisco.training.cmad.blog.model;

import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;
import lombok.ToString;
import org.bson.types.ObjectId;
import org.mongodb.morphia.annotations.Embedded;
import org.mongodb.morphia.annotations.Entity;
import org.mongodb.morphia.annotations.Id;
import org.mongodb.morphia.annotations.Indexed;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by satkuppu on 4/23/16.
 */
@Entity(value = "companies", noClassnameStored=true)
@ToString
public class Company {

    @Id @Getter
    private ObjectId id;

    @Getter @Setter @NonNull
    @Indexed(unique = true, name = "companyName")
    private String name;

    @Embedded
    private Map<ObjectId, Site> sites;

    public Company() {
    }

    public Company(String name) {
        this.name = name;
        this.sites = new HashMap<>();
    }

    public Company addSite(Site site) {
        if(! this.sites.containsValue(site)) {
            this.sites.put(site.getId(), site);
        }
        return this;
    }

    public Collection<Site> getSites() {
        return this.sites.values();
    }

    public Site getSite(ObjectId siteId) {
        return this.sites.get(siteId);
    }

}
