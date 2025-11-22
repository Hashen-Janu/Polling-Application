package com.poll.poll_Spring_Boot.dtos;


import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class PollDTO {
    private Long id;
    private String question;
    private Date postedDate;
    private Date expiredDate;
    private Date expiredAt;

    private List<String> options;

    private Integer totalVoteCount;
    private boolean isExpired;
    private Long userId;
    private String username;
    private List<OptionsDTO> optionsDTOS;
    private boolean voted;



}
