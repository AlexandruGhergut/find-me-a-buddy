package com.fmi.findmeabuddy.repository;

import com.fmi.findmeabuddy.domain.Profile;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProfileRepository extends JpaRepository<Profile, Long> {

}

