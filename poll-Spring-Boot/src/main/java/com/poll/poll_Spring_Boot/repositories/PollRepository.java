package com.poll.poll_Spring_Boot.repositories;

import com.poll.poll_Spring_Boot.entities.Poll;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PollRepository extends JpaRepository<Poll,Long> {

}
