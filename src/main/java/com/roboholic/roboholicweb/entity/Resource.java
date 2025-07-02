package com.roboholic.roboholicweb.entity;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.CollectionTable;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.MapsId;
import jakarta.persistence.OneToOne;
import jakarta.persistence.PrimaryKeyJoinColumn;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Size;
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
    private Long resourceID;

    @Column(name = "resource_name")
    private String resourceName;

    @Column(name = "resource_description", length = 2000)
    @Size(max = 2000, message = "Description muct be less than 2000 characters")
    private String resourceDescription;

    @Column(name = "date_upload")
    private LocalDateTime dateUploaded;

    @Column(name = "link_url")
    private String linkUrl;

    @ElementCollection
    @CollectionTable(name = "resource_documents", joinColumns = @JoinColumn(name = "resource_id"))
    @Column(name = "document_url")
    private List<String> documentUrls;

    // public Resource(String resourceName, LocalDateTime dateUploaded) {
    //     this.resourceName = resourceName;
    //     this.dateUploaded = dateUploaded;
    // }

    public Resource(String resourceName, String resourceDescription, LocalDateTime dateUploaded) {
        this.resourceName = resourceName;
        this.resourceDescription = resourceDescription;
        this.dateUploaded = dateUploaded;
    }

    public List<String> getDocumentUrls() {
        return documentUrls;
    }

    public void setDocumentUrls(List<String> documentUrls) {
        this.documentUrls = documentUrls;
    }
    //First method
    // @OneToOne(mappedBy = "resources",cascade = CascadeType.ALL)
    // @PrimaryKeyJoinColumn // primary key will be used as foreign key in resource
    // private Item item;

    // @OneToOne(cascade = CascadeType.ALL)
    // @JoinColumn(name = "resource_id")
    // @MapsId // Indicates that the primary key values will be copied from the Item entity
    // private Item item;

    //Second method using join table
    // @OneToOne(cascade = CascadeType.ALL)
    // @JoinTable(name = "resource_items",
    //     joinColumns = {@JoinColumn(name = "resource_id", referencedColumnName = "resource_id")},
    //     inverseJoinColumns = {@JoinColumn(name = "product_id", referencedColumnName = "product_id")})
    // private Item item;

    // @OneToOne(mappedBy = "resource")
    // private Item item;

}
