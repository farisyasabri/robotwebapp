package com.roboholic.roboholicweb.entity;

import java.util.Date;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Entity
@Table(name = "resources")
public class Resource {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "resource_id")
    private int resourceID;

    @Column(name = "resource_name")
    private String resourceName;

    @Column(name = "resource_description")
    private String resourceDescription;

    @Column(name = "date_upload")
    private Date dateUploaded;
    

    public Resource(String resourceName, Date dateUploaded) {
        this.resourceName = resourceName;
        this.dateUploaded = dateUploaded;
    }

}
