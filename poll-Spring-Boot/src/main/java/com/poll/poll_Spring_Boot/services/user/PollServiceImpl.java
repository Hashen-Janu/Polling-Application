package com.poll.poll_Spring_Boot.services.user;

import com.poll.poll_Spring_Boot.dtos.OptionsDTO;
import com.poll.poll_Spring_Boot.dtos.PollDTO;
import com.poll.poll_Spring_Boot.entities.Options;
import com.poll.poll_Spring_Boot.entities.Poll;
import com.poll.poll_Spring_Boot.entities.User;
import com.poll.poll_Spring_Boot.repositories.OptionsRepository;
import com.poll.poll_Spring_Boot.repositories.PollRepository;
import com.poll.poll_Spring_Boot.repositories.VoteRepository;
import com.poll.poll_Spring_Boot.utils.JWTUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor

public class PollServiceImpl implements PollService{

    private final JWTUtil jwtUtil;

    private final PollRepository pollRepository;

    private final OptionsRepository optionsRepository;

     private final JavaMailSender javaMailSender;

     private final VoteRepository voteRepository;

     public PollDTO getPollDTOInService(Poll poll){
         User loggedInUser = jwtUtil.getLoggedInUser();
         PollDTO pollDTO = new PollDTO();
         pollDTO.setId(poll.getId());
         pollDTO.setQuestion(poll.getQuestion());
         pollDTO.setExpiredAt(poll.getExpiredAt());
         pollDTO.setExpired(poll.getExpiredAt() != null && poll.getExpiredAt().before(new Date()));
         pollDTO.setPostedDate(poll.getPostedDate());
         pollDTO.setOptionsDTOS(poll.getOptions().stream().map(options -> this.getOptionDTO(options,
                 loggedInUser.getId(), poll.getId())).collect(Collectors.toList()));
         pollDTO.setTotalVoteCount(poll.getTotalVoteCount());

         User pollOwner = poll.getUser(); //User who posted the poll
         //Check if logged-in user is the poll owner
         if(loggedInUser != null && pollOwner.getId().equals(loggedInUser.getId())){
             pollDTO.setUsername("you");
         }else {
             pollDTO.setUsername(pollOwner.getFirstname() + " " + pollOwner.getLastname());
         }

         pollDTO.setUserId(pollOwner.getId());

         //Check if logged-in user has voted
         if (loggedInUser != null){
             pollDTO.setVoted(voteRepository.existsByPollIdAndUserId(poll.getId(), loggedInUser.getId()));
         }
         return pollDTO;
     }

    public OptionsDTO getOptionDTO(Options options, Long userId, Long pollId){
        OptionsDTO optionsDTO = new OptionsDTO();
        optionsDTO.setId(options.getId());
        optionsDTO.setTitle(options.getTitle());
        optionsDTO.setPollId(options.getPoll().getId());
        optionsDTO.setVoteCount(options.getVoteCount());
        optionsDTO.setUserVotedThisOption(voteRepository.existsByPollIdAndUserIdAndOptionsId(pollId, userId, options.getId()));
        return optionsDTO;
    }

}
