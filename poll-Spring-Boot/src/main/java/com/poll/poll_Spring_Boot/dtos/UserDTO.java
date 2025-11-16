package com.poll.poll_Spring_Boot.dtos;

import lombok.Data;

@Data
public class UserDTO {

    private Long id;

    private String email;

    private String firstname;
    private String lastname;
}
