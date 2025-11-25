package com.poll.poll_Spring_Boot.services.user;

import com.poll.poll_Spring_Boot.dtos.PollDTO;

public interface PollService {
    PollDTO postPoll(PollDTO pollDTO);

}
