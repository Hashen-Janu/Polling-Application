package com.poll.poll_Spring_Boot.services.user;

import com.poll.poll_Spring_Boot.dtos.PollDTO;

import java.util.List;

public interface PollService {
    PollDTO postPoll(PollDTO pollDTO);

    void deletePoll(Long id);

    List<PollDTO> getAllPolls();

    List<PollDTO> getMyPolls();


}
