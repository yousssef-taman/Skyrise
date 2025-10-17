package com.example.backend.Controllers;

import com.example.backend.DTOs.FeedbackDTO;
import com.example.backend.Services.FeedbackService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@Controller
@RequestMapping
public class FeedbackController {
    private final FeedbackService feedbackService;

    @PostMapping("add/feedback")
    public ResponseEntity<Void> addFeedback(@RequestBody FeedbackDTO feedback) {
            feedbackService.addFeedback(feedback);
            return ResponseEntity.ok().build();
    }

}
