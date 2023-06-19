package com.roboholic.roboholicweb.service;

import java.util.List;

import org.springframework.data.domain.Page;

import com.roboholic.roboholicweb.dto.UserRegistrationDTO;
import com.roboholic.roboholicweb.entity.User;

public interface UserService {
    public User add(UserRegistrationDTO userRegistrationDTO);

    public List<User> getAllUsers();

    public User getUserById(Long id);

    public User updateUserById(Long id, User user);

    public void deleteUserById(Long id);

    // public User updateByEmail(String email, User user);

    public void deleteByEmail(String email);

    public User getUserByEmail(String email);

    public Page<User> findPaginated(int pageNo, int pageSize, String sortField, String sortDirection);
}
