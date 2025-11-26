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
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import java.net.SocketTimeoutException;
import java.sql.SQLOutput;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor

public class PollServiceImpl implements PollService{

    private final JWTUtil jwtUtil;

    private final PollRepository pollRepository;

    private final OptionsRepository optionsRepository;

     private final JavaMailSender javaMailSender;

     private final VoteRepository voteRepository;

    @Override
    public PollDTO postPoll(PollDTO pollDTO) {
        User user = jwtUtil.getLoggedInUser();
        if (user != null){
            Poll poll = new Poll();
            poll.setQuestion(pollDTO.getQuestion());
            poll.setPostedDate(new Date());
            poll.setExpiredAt(pollDTO.getExpiredAt());
            poll.setUser(user);
            poll.setTotalVoteCount(0);
            Poll createdPoll = pollRepository.save(poll);

            List<Options> options = new ArrayList<>();
            for(String optionTitle : pollDTO.getOptions()){
                Options option = new Options();
                option.setTitle(optionTitle);
                option.setPoll(createdPoll);
                option.setVoteCount(0);
                options.add(option);

            }

            List<Options> savedOptions = optionsRepository.saveAll(options);
            poll.setOptions(savedOptions);
            pollRepository.save(poll);

            if (createdPoll.getId() != null){
                try {
                    MimeMessage mimeMessage = javaMailSender.createMimeMessage();
                    MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage,true);
                    mimeMessageHelper.setFrom("sender.email099@gmail.com");
                    mimeMessageHelper.setTo(user.getEmail());
                    mimeMessageHelper.setSubject("new poll posted");
                    mimeMessageHelper.setText("Dear " + user.getFirstname() + "!I trust this message find you in good spirits." +
                            "I wanted to inform you that a new poll has been successfully posted." +
                            "The question you submitted is as follows:'" + createdPoll.getQuestion() + "' . " +
                            "and it is scheduled to expire on " + createdPoll.getPostedDate() +"' " +
                            "Thank you for your engagement and contribution to our platform. POll APP");
                    javaMailSender.send(mimeMessage);
                    System.out.println("Mail sent to " + user.getEmail());
                } catch (MessagingException e){
                    System.out.println("Failed send email: " + e.getMessage());
                }
            }
            return getPollDTOInService(createdPoll);
        }
        return null;
    }

    @Override
    public void deletePoll(Long id) {
        pollRepository.deleteById(id);

    }

    @Override
    public List<PollDTO> getAllPolls() {
        return pollRepository.findAll()
                .stream()
                .sorted(Comparator.comparing(Poll::getPostedDate).reversed())
                .map(this::getPollDTOInService)
                .collect(Collectors.toList());

    }

    @Override
    public List<PollDTO> getMyPolls() {
        User user = jwtUtil.getLoggedInUser();
        if (user != null) {
            return pollRepository.findAllByUserId(user.getId())
                    .stream()
                    .sorted(Comparator.comparing(Poll::getPostedDate).reversed())
                    .map(this::getPollDTOInService)
                    .collect(Collectors.toList());
        }
        throw new EntityNotFoundException("User not found");
    }

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
