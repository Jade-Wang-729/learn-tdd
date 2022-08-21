package com.learn.tdd.controller;

import com.learn.tdd.controller.request.NotificationRequest;
import com.learn.tdd.service.NotificationService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import javax.validation.Valid;

@RestController
@RequestMapping("/notify")
@AllArgsConstructor
public class NotifyController {

    private NotificationService notificationService;

    @PostMapping
    public ResponseEntity<String> notify(@RequestBody @Valid NotificationRequest notification) {
        String message =  notificationService.notify(notification.getId(),notification.getContent(),notification.getStatus());

        if (message.equals(notificationService.SUCCESS)) {
            return ResponseEntity.ok(notificationService.SUCCESS);
        }
        return ResponseEntity.badRequest().body(message);
    }


}
