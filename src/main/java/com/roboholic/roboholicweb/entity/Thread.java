package com.roboholic.roboholicweb.entity;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Entity
@Table(name = "forum_thread")
public class Thread {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name ="thread_id")
    private Long threadID;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "thread", referencedColumnName = "thread_id")
    private List<Post> posts = new ArrayList<>();
}

/*
 * thread ||--------|< post
 */
