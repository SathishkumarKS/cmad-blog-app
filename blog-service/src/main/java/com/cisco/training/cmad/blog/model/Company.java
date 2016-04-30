package com.cisco.training.cmad.blog.model;

import com.google.common.base.Preconditions;
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
import java.util.Optional;

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

    @Getter
    private String subDomain;

    @Embedded
    private Map<ObjectId, Site> sites;

    public Company() {
    }

    public Company(String name) {
        Preconditions.checkNotNull(name, "Company name can't be empty");
        this.name = name;
        this.sites = new HashMap<>();
    }

    public Company addSite(Site site) {
        if(! this.sites.containsValue(site)) {
            this.sites.put(site.getId(), site);
        }
        return this;
    }

    public Company withSubDomain(String subDomain) {
        this.subDomain = subDomain;
        return this;
    }

    public Optional<Map<ObjectId, Site>> getSites() {
        return Optional.ofNullable(this.sites);
    }

    public Optional<Site> getSite(ObjectId siteId) {
        return Optional.ofNullable(this.sites.get(siteId));
    }

}
