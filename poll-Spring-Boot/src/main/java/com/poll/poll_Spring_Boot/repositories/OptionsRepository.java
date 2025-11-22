package com.poll.poll_Spring_Boot.repositories;

import com.poll.poll_Spring_Boot.entities.Options;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OptionsRepository extends JpaRepository<Options, Long> {

}
