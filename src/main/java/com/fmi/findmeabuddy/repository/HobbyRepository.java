package com.fmi.findmeabuddy.repository;

import com.fmi.findmeabuddy.domain.Hobby;
import org.springframework.data.jpa.repository.JpaRepository;

public interface HobbyRepository extends JpaRepository<Hobby, Long> {
}
