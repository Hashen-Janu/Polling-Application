package com.poll.poll_Spring_Boot.controllers.user;

import com.poll.poll_Spring_Boot.services.user.PollService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/user")
@CrossOrigin("*")
public class PollController {

    private final PollService pollService;
}
