package com.example.backend.DTOs.Criteria;

import com.example.backend.Enums.QualityRating;

public record FeedbackFilterCriteria(
        Short stars,
        String direction,
        QualityRating comfort,
        QualityRating service,
        QualityRating punctuality,
        QualityRating foodAndBeverage,
        QualityRating cleanliness) {

}
