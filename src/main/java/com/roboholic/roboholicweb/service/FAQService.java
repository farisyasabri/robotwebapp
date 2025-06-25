package com.roboholic.roboholicweb.service;

import java.util.List;

import com.roboholic.roboholicweb.entity.FAQ;

public interface FAQService {
    public Long addFAQ(FAQ faq);
    public void deleteFAQ(long faq_id);
    public FAQ getFAQbyId(long faq_id);
    public List<FAQ> getAllFAQs();
    public Long updateFAQ(FAQ faq, Long faq_id);
    public List<FAQ> searchFAQsByCategory(String category);
    public List<FAQ> searchFAQsByQuestion(String searchTerm);
}