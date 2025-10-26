package com.example.backend.Services;

import com.example.backend.Utilites.PageFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import com.example.backend.DTOMappers.FlightSearchMapper;
import com.example.backend.DTOMappers.PageResponseMapper;
import com.example.backend.DTOs.PageResponse.PageResponse;
import com.example.backend.DTOs.Criteria.FlightSearchCriteria;
import com.example.backend.DTOs.FlightSearchResponse;
import com.example.backend.Entities.Flight;
import com.example.backend.Enums.SeatClass;
import com.example.backend.Repositories.FlightRepository;
import com.example.backend.Specifications.FlightSpecifications;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class FlightSearchService {
        private final FlightRepository flightRepository;
        private final FlightSearchMapper flightSearchMapper;

    public PageResponse<FlightSearchResponse> getFlights(FlightSearchCriteria criteria, int pageNumber) {

        Specification<Flight> spec = buildFlightSpecifications(criteria);
        Pageable pageable = buildPageRequest(criteria, pageNumber);

        Page<Flight> flightPage = flightRepository.findAll(spec, pageable);
        Page<FlightSearchResponse> dtoPage = flightPage.map(
                flightSearchMapper::toDTO
        );

        return PageResponseMapper.toPageResponse(dtoPage);
    }


    private Sort handleSorting(String sortby, String direction, SeatClass seatClass) {
                if (sortby == null)
                        return null;

                if (direction == null)
                        direction = "asc";
                else
                    direction = direction.toLowerCase();

                if ("price".equals(sortby))
                        sortby = (seatClass == SeatClass.ECONOMY) ? "economyPrice" : "businessPrice";
                else
                        return null;
                return Sort.by(Sort.Direction.fromString(direction), sortby);
        }

    private Specification<Flight> buildFlightSpecifications(FlightSearchCriteria criteria) {
        Specification<Flight> spec = Specification.where(null);

        if (criteria.departureDate() != null)
            spec = spec.and(FlightSpecifications.hasDepartureDate(criteria.departureDate()));

        if (criteria.departureAirportId() != null)
            spec = spec.and(FlightSpecifications.hasDepartureAirport(criteria.departureAirportId()));

        if (criteria.arrivalAirportId() != null)
            spec = spec.and(FlightSpecifications.hasArrivalAirport(criteria.arrivalAirportId()));

        if (criteria.seatClass() != null)
            spec = spec.and(FlightSpecifications.hasAvailableSeats(criteria.seatClass(), criteria.numberOfTickets()));

        if (criteria.flightType() != null)
            spec = spec.and(FlightSpecifications.hasFlightType(criteria.flightType()));

        return spec;
    }

    private Pageable buildPageRequest(FlightSearchCriteria criteria, int pageNumber) {
        Sort sort = handleSorting(
                criteria.sortby(),
                criteria.direction(),
                criteria.seatClass()
        );
        return PageFactory.create(pageNumber, null, sort);
    }

}
