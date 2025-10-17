package com.example.backend.Services.AdminDashboard;

import com.example.backend.Services.FeedbackDisplayService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import com.example.backend.Repositories.FeedbackRepository;

@ExtendWith(MockitoExtension.class)
public class FeedbackDisplayServiceTest {

    @InjectMocks
    private FeedbackDisplayService feedbackDisplayService;

    @Mock
    private FeedbackRepository feedbackRepository;

    @Test
    void testGetAverageRating() {
        // given
        double expectedAvg = 3;
        // mock the calls
        Mockito.when(feedbackRepository.getAvgRating()).thenReturn(expectedAvg);
        // when
        short actualAvg = feedbackDisplayService.getAverageRating();
        // then
        Assertions.assertEquals(expectedAvg, actualAvg);
    }


}