package com.fmi.findmeabuddy.handler.info;

import com.fmi.findmeabuddy.domain.Hobby;
import com.fmi.findmeabuddy.repository.HobbyRepository;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class HobbyHandler {
    private final HobbyRepository hobbyRepository;

    public HobbyHandler(HobbyRepository hobbyRepository) {
        this.hobbyRepository = hobbyRepository;
    }

    @GetMapping(value = "/info/hobby",
            produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Hobby> getHobbies() {
        return hobbyRepository.findAll();
    }
}
