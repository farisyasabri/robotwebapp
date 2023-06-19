package com.roboholic.roboholicweb.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class UserRegistrationDTO {
    
    private Long id;
    private String fname;
    private String lname;
    private String password;
    private String email;
    private String address;
    private String phoneNumber;
    private String role;

}
