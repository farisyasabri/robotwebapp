package com.roboholic.roboholicweb.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.roboholic.roboholicweb.dto.UserRegistrationDTO;
import com.roboholic.roboholicweb.entity.User;
import com.roboholic.roboholicweb.repository.UserRepository;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserRepository userRepository;

    // adding new user
    @Override
    public User add(UserRegistrationDTO registrationDto) {
        User user = null;
        if (userRepository.findByEmail(registrationDto.getEmail()) != null) {
            // return new Exception();
        } else {
            user = new User(
                    registrationDto.getFname(),
                    registrationDto.getLname(),
                    // passwordEncoder.encode(registrationDto.getPassword()),
                    registrationDto.getPassword(),
                    registrationDto.getEmail(),
                    registrationDto.getPhoneNumber(), "ROLE_USER");

        }

        return userRepository.save(user);
    }

    @Override
    public void deleteUserById(Long id) {
        userRepository.deleteById(id);
    }

    @Override
    public User updateUserById(Long id, User user){
        User update = userRepository.findById(id).get();
        update.setFname(user.getFname());
        update.setLname(user.getLname());
        update.setEmail(user.getEmail());
        update.setAddress(user.getAddress());
        update.setPhoneNumber(user.getPhoneNumber());
        update.setPassword(user.getPassword());

        return userRepository.save(update);
    }

    @Override
    public Page<User> findPaginated(int pageNo, int pageSize, String sortField,
            String sortDirection) {
        Sort sort = sortDirection.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortField).ascending()
                : Sort.by(sortField).descending();
        Pageable pageable = PageRequest.of(pageNo - 1, pageSize, sort);

        return this.userRepository.findAll(pageable);
    }

    @Override
    public User getUserById(Long id) {
        return userRepository.findById(id).orElse(null);
    }

    @Override
    public void deleteByEmail(String email) {
        User user = userRepository.findByEmail(email);
        userRepository.deleteById(user.getUserID());
    }


    @Override
    public List<User> getAllUsers() {
        List<User> allUsers = new ArrayList<>();
        userRepository.findAll().forEach(allUsers::add);
        return allUsers;
    }

    @Override
    public User getUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }

}