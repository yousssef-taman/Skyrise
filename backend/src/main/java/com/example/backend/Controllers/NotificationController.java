package com.example.backend.Controllers;

import com.example.backend.DTOMappers.PageResponseMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.backend.DTOs.NotificationDTO;
import com.example.backend.DTOs.PageResponse.PageResponse;
import com.example.backend.Services.NotificationService;

import lombok.RequiredArgsConstructor;

@Controller
@RequestMapping("/notifications")
@RequiredArgsConstructor
public class NotificationController {
    private final int PAGE_SIZE = 10 ;

    private final NotificationService notificationService;

    @GetMapping("/{userId}")
    public ResponseEntity<PageResponse<NotificationDTO>> getNotifications(@PathVariable Integer userId, @RequestParam(defaultValue = "0") int pageNumber) {
        Pageable pageable = PageRequest.of(pageNumber,PAGE_SIZE);
        Page<NotificationDTO> notifications = notificationService.getNotification(userId, pageable);
        return ResponseEntity.ok(PageResponseMapper.toPageResponse(notifications));
    }

//    @PutMapping("/{userId}")
//    public ResponseEntity<String> markAsRead(@PathVariable Integer userId, @RequestParam Integer notificationId, @RequestParam int numOfNotifications) {
//        notificationService.markAsRead(userId, notificationId, numOfNotifications);
//        return ResponseEntity.ok("Marked as read");
//    }
}
