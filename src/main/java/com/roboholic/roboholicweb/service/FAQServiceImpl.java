package com.roboholic.roboholicweb.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.roboholic.roboholicweb.entity.FAQ;
import com.roboholic.roboholicweb.repository.FAQRepository;

@Service
public class FAQServiceImpl implements FAQService {
    
    @Autowired
    private FAQRepository faqRepo;

    @Override
    public Long addFAQ(FAQ faq) {
        return faqRepo.save(faq).getFaqID();
    }

    @Override
    public void deleteFAQ(long faq_id) {
        faqRepo.deleteById(faq_id);
    }

    @Override
    public FAQ getFAQbyId(long faq_id) {
        Optional<FAQ> optional = faqRepo.findById(faq_id);
        return optional.orElseThrow(() -> new RuntimeException("FAQ not found for id: " + faq_id));
    }

    @Override
    public List<FAQ> getAllFAQs() {
        List<FAQ> faqs = new ArrayList<>();
        faqRepo.findAll().forEach(faqs::add);
        return faqs;
    }

    @Override
    public Long updateFAQ(FAQ faq, Long faq_id) {
        Optional<FAQ> optional = faqRepo.findById(faq_id);
        if(optional.isPresent()) {
            FAQ existingFAQ = optional.get();
            existingFAQ.setQuestion(faq.getQuestion());
            existingFAQ.setAnswer(faq.getAnswer());
            existingFAQ.setCategory(faq.getCategory());
            faqRepo.save(existingFAQ);
        }
        return faq_id;
    }

    @Override
    public List<FAQ> searchFAQsByCategory(String category) {
        return faqRepo.findByCategory(category);
    }

    @Override
    public List<FAQ> searchFAQsByQuestion(String searchTerm) {
        return faqRepo.findByQuestionContaining(searchTerm);
    }
}