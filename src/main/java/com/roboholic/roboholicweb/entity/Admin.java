package com.roboholic.roboholicweb.entity;

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
@Table(name = "admin")
public class Admin {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "admin_id")
    private int adminID;

    @Column(name = "admin_name")
    private String adminName;

    @Column(name = "admin_password")
    private String adminPassword;

    // @OneToMany(cascade = CascadeType.ALL)
    // @JoinColumn(name = "author", referencedColumnName = "admin_id")
    // private List<Post> forumPosts = new ArrayList<>();

}
