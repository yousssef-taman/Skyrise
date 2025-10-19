package com.example.backend.Controllers;

import com.example.backend.Utilites.ValidateInput;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.backend.DTOs.PageResponse.PageResponse;
import com.example.backend.DTOs.FlightSearchResponse;
import com.example.backend.DTOs.Criteria.FlightSearchCriteria;
import com.example.backend.Services.FlightSearchService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@Controller
@RequestMapping("/user")
@RequiredArgsConstructor
public class FlightSearchController {
    private final FlightSearchService flightSearchService;

    @PostMapping("/search")
    public ResponseEntity<PageResponse<FlightSearchResponse>> getFlights(
            @RequestParam(defaultValue = "0") int pageNumber,
            @Valid @RequestBody FlightSearchCriteria flightSearchCriteria) {
                
        ValidateInput.validatePageNumber(pageNumber);
        PageResponse<FlightSearchResponse> page = flightSearchService.getFlights(flightSearchCriteria, pageNumber);
        return ResponseEntity.ok(page);
    }

}
