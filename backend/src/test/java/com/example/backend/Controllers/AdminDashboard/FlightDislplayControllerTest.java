package com.example.backend.Controllers.AdminDashboard;

import com.example.backend.Controllers.FlightDisplayController;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import com.example.backend.Services.FlightDisplayService;

@WebMvcTest(FlightDisplayController.class)
public class FlightDislplayControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private FlightDisplayService flightService;
    
    @Test
    void testFilter() {

    }

    @Test
    void testGetFlightsByDepartureDate() {

    }
}
