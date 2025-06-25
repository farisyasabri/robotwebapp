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
@Table(name = "faq")
public class FAQ {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "faq_id")
    private Long faqID;

    @Column(name = "question", nullable = false)
    private String question;

    @Column(name = "answer", nullable = false, length = 1000)
    private String answer;

    @Column(name = "category")
    private String category;

    public FAQ(String question, String answer, String category) {
        this.question = question;
        this.answer = answer;
        this.category = category;
    }
}