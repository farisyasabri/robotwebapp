package com.roboholic.roboholicweb.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.roboholic.roboholicweb.entity.FAQ;

@Repository
public interface FAQRepository extends JpaRepository<FAQ, Long> {
    List<FAQ> findByCategory(String category);
    List<FAQ> findByQuestionContaining(String searchTerm);
}