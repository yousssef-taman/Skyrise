package com.example.backend.Services;

import com.example.backend.Utilites.PageFactory;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import com.example.backend.Repositories.FeedbackRepository;
import com.example.backend.Specifications.FeedbackSpecifications;
import com.example.backend.Utilites.Utilities;
import lombok.RequiredArgsConstructor;
import com.example.backend.DTOMappers.FeedbackMapper;
import com.example.backend.DTOMappers.PageResponseMapper;
import com.example.backend.DTOs.FeedbackDTO;
import com.example.backend.DTOs.PageResponse.PageResponse;
import com.example.backend.DTOs.Criteria.FeedbackFilterCriteria;
import com.example.backend.Entities.Feedback;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;

@RequiredArgsConstructor
@Service
public class FeedbackDisplayService {

    private final FeedbackRepository feedbackRepository;
    private final FeedbackMapper feedBackMapper;

    public short getAverageRating() {
        Double averageRating = feedbackRepository.getAvgRating();
        return averageRating == null ? 0 : averageRating.shortValue();
    }

    public PageResponse<FeedbackDTO> getAll(int pageNumber) {

        Pageable pageable = PageRequest.of(pageNumber, 10);
        Page<Feedback> page = feedbackRepository.findAll(pageable);
        Page<FeedbackDTO> pageDTO = page.map(feedBackMapper::toDTO);
        return PageResponseMapper.toPageResponse(pageDTO);

    }

    public PageResponse<FeedbackDTO> filterFeedback(FeedbackFilterCriteria feedbackFilterDTO, int pageNumber) {

        Specification<Feedback> spec = Specification.where(null);

        spec = buildFilterSpecifications(spec, feedbackFilterDTO);

        Pageable pageable = createPageableFromFilter(feedbackFilterDTO, pageNumber);

        Page<Feedback> page = feedbackRepository.findAll(spec, pageable);
        Page<FeedbackDTO> pageDTO = page.map(feedBackMapper::toDTO);
        return PageResponseMapper.toPageResponse(pageDTO);
    }

    private Specification<Feedback> buildFilterSpecifications(Specification<Feedback> spec, FeedbackFilterCriteria feedbackFilterDTO) {
        if (feedbackFilterDTO.stars() != null)
            spec = spec.and(FeedbackSpecifications.containsStars(feedbackFilterDTO.stars()));

        if (feedbackFilterDTO.service() != null)
            spec = spec.and(FeedbackSpecifications.containsService(feedbackFilterDTO.service()));

        if (feedbackFilterDTO.comfort() != null)
            spec = spec.and(FeedbackSpecifications.containsComfort(feedbackFilterDTO.comfort()));

        if (feedbackFilterDTO.punctuality() != null)
            spec = spec.and(FeedbackSpecifications.containsPunctuality(feedbackFilterDTO.punctuality()));

        if (feedbackFilterDTO.cleanliness() != null)
            spec = spec.and(FeedbackSpecifications.containsCleanliness(feedbackFilterDTO.cleanliness()));

        if (feedbackFilterDTO.foodAndBeverage() != null)
            spec = spec.and(FeedbackSpecifications.containsFoodAndBeverage(feedbackFilterDTO.foodAndBeverage()));

        return spec;
    }

    private Pageable createPageableFromFilter(FeedbackFilterCriteria feedbackFilterDTO, int pageNumber) {
        String sortDirection = feedbackFilterDTO.direction();
        Sort sort = Utilities.sort(sortDirection, "dateOfCreation");
        return PageFactory.create(pageNumber, null, sort);
    }
}

