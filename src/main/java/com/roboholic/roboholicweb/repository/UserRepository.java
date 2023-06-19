package com.roboholic.roboholicweb.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.roboholic.roboholicweb.entity.User;


public interface UserRepository extends JpaRepository<User, Long>{
    User findByEmail(String email);
    User findByFname(String fname);
    User findByLname(String lname);
    User findByPhoneNumber(String phoneNumber);
    User findByAddress(String address);

}
