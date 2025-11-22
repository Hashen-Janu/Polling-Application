package com.poll.poll_Spring_Boot.dtos;

import lombok.Data;

@Data
public class SignupRequest {

    private String email;
    private String password;
    private String firstname;
    private String lastname;
}
