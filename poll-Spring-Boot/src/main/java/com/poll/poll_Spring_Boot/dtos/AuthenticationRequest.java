package com.poll.poll_Spring_Boot.dtos;

import lombok.Data;

@Data
public class AuthenticationRequest {

    private String email;

    private String password;
}
