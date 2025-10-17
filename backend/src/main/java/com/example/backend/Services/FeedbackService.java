package com.example.backend.Services;

import com.example.backend.DTOMappers.FeedbackMapper;
import com.example.backend.DTOs.FeedbackDTO;
import com.example.backend.Entities.*;
import com.example.backend.Repositories.FeedbackRepository;
import com.example.backend.Repositories.FlightRepository;
import com.example.backend.Repositories.UserRepository;
import com.example.backend.Constants.ExceptionMessages;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class FeedbackService {

    private final FeedbackRepository feedbackRepository;
    private final FlightRepository flightRepository;
    private final UserRepository userRepository;
    private final FeedbackMapper feedbackMapper;

    public void  addFeedback(FeedbackDTO feedbackDTO) {

        Flight flight = this.flightRepository.findFlightByFlightId(feedbackDTO.flightId()).
                                orElseThrow(() -> new IllegalArgumentException(ExceptionMessages.FLIGHT_NOT_FOUND));

        FlightLeg.User user = this.userRepository.findUserByUserId(feedbackDTO.userId())
                .orElseThrow(() -> new IllegalArgumentException(ExceptionMessages.USER_NOT_FOUND));

        Feedback feedback = feedbackMapper.toEntity(feedbackDTO);
        feedback.setFlight(flight);
        feedback.setUser(user);

        this.feedbackRepository.save(feedback);
    }
}
