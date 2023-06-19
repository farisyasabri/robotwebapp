package com.roboholic.roboholicweb.entity;

import org.springframework.format.annotation.NumberFormat;
import org.springframework.format.annotation.NumberFormat.Style;
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
@Table(name = "user")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long userID;

    @Column(name = "first_name")
    private String fname;

    @Column(name = "last_name")
    private String lname;

    @Column(name = "password")
    private String password;

    @Column(name = "email")
    private String email;

    @Column(name = "address")
    private String address;

    @Column(name = "phone_number")
    @NumberFormat(style = Style.NUMBER)
    private String phoneNumber;

    @Column(name = "role")
    private String role;

    // @OneToMany(cascade = CascadeType.ALL)
    // @JoinColumn(name = "author", referencedColumnName = "user_id")
    // private List<Post> forumPosts = new ArrayList<>();

    // @OneToMany(cascade = CascadeType.ALL)
    // @JoinColumn(name = "owned_by", referencedColumnName = "user_id")
    // private List<Cart> carts = new ArrayList<>();

    // @ManyToOne
    // private Rank rank;

    // @Column(name = "image")
    // private byte[] image;

    // @Column(name = "collected_points")
    // private int points;
    

    public User(String fname, String lname, String password, String email, String address, String phoneNumber, String role) {
        this.fname = fname;
        this.lname = lname;
        this.password = password;
        this.email = email;
        this.address = address;
        this.phoneNumber = phoneNumber;
        this.role = role;
    }


    public User(String fname, String lname, String password, String email, String phoneNumber, String role) {
        this.fname = fname;
        this.lname = lname;
        this.password = password;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.role = role;
    }


}

