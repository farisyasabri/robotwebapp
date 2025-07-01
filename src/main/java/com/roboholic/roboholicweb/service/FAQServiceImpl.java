package com.roboholic.roboholicweb.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import com.roboholic.roboholicweb.entity.FAQ;
import com.roboholic.roboholicweb.repository.FAQRepository;

@Service
public class FAQServiceImpl implements FAQService {
    
    private static final Logger logger = LoggerFactory.getLogger(FAQServiceImpl.class);
    @Autowired
    private FAQRepository faqRepo;

    @Override
    public Long addFAQ(FAQ faq) {
        // return faqRepo.save(faq).getFaqID();
        try {
            return faqRepo.save(faq).getFaqID();
        } catch (DataIntegrityViolationException e) {
            logger.error("Data integrity violation while adding FAQ", e);
            throw new RuntimeException("Failed to add FAQ due to data integrity violation");
        } catch (Exception e) {
            logger.error("Unexpected error while adding FAQ", e);
            throw new RuntimeException("Failed to add FAQ");
        }
    }

    @Override
    public void deleteFAQ(long faq_id) {
        // faqRepo.deleteById(faq_id);
        try {
            if (!faqRepo.existsById(faq_id)) {
                throw new RuntimeException("FAQ not found with id: " + faq_id);
            }
            faqRepo.deleteById(faq_id);
        } catch (EmptyResultDataAccessException e) {
            logger.error("FAQ not found with id: " + faq_id, e);
            throw new RuntimeException("FAQ not found with id: " + faq_id);
        } catch (Exception e) {
            logger.error("Error deleting FAQ with id: " + faq_id, e);
            throw new RuntimeException("Failed to delete FAQ");
        }
    }

    @Override
    public FAQ getFAQbyId(long faq_id) {
        // Optional<FAQ> optional = faqRepo.findById(faq_id);
        // return optional.orElseThrow(() -> new RuntimeException("FAQ not found for id: " + faq_id));
        try {
            return faqRepo.findById(faq_id)
                    .orElseThrow(() -> new RuntimeException("FAQ not found for id: " + faq_id));
        } catch (Exception e) {
            logger.error("Error fetching FAQ with id: " + faq_id, e);
            throw new RuntimeException("Error fetching FAQ");
        }
    }

    @Override
    public List<FAQ> getAllFAQs() {
        // List<FAQ> faqs = new ArrayList<>();
        // faqRepo.findAll().forEach(faqs::add);
        // return faqs;
        try {
            List<FAQ> faqs = new ArrayList<>();
            faqRepo.findAll().forEach(faqs::add);
            return faqs;
        } catch (Exception e) {
            logger.error("Error fetching all FAQs", e);
            throw new RuntimeException("Error fetching FAQs");
        }
    }

    @Override
    public Long updateFAQ(FAQ faq, Long faq_id) {
        // Optional<FAQ> optional = faqRepo.findById(faq_id);
        // if(optional.isPresent()) {
        //     FAQ existingFAQ = optional.get();
        //     existingFAQ.setQuestion(faq.getQuestion());
        //     existingFAQ.setAnswer(faq.getAnswer());
        //     existingFAQ.setCategory(faq.getCategory());
        //     faqRepo.save(existingFAQ);
        // }
        // return faq_id;
        try {
            FAQ existingFAQ = faqRepo.findById(faq_id)
                    .orElseThrow(() -> new RuntimeException("FAQ not found for id: " + faq_id));
            
            existingFAQ.setQuestion(faq.getQuestion());
            existingFAQ.setAnswer(faq.getAnswer());
            existingFAQ.setCategory(faq.getCategory());
            
            return faqRepo.save(existingFAQ).getFaqID();
        } catch (Exception e) {
            logger.error("Error updating FAQ with id: " + faq_id, e);
            throw new RuntimeException("Error updating FAQ");
        }
    }

    @Override
    public List<FAQ> searchFAQsByCategory(String category) {
        // return faqRepo.findByCategory(category);
        try {
            return faqRepo.findByCategory(category);
        } catch (Exception e) {
            logger.error("Error searching FAQs by category: " + category, e);
            throw new RuntimeException("Error searching FAQs");
        }
    }

    @Override
    public List<FAQ> searchFAQsByQuestion(String searchTerm) {
        // return faqRepo.findByQuestionContaining(searchTerm);
        try {
            return faqRepo.findByQuestionContaining(searchTerm);
        } catch (Exception e) {
            logger.error("Error searching FAQs by question: " + searchTerm, e);
            throw new RuntimeException("Error searching FAQs");
        }
    }
}