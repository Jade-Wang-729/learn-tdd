package com.learn.tdd.controller;


import com.learn.tdd.controller.request.NotificationRequest;
import com.learn.tdd.controller.request.NotificationSearchRequest;
import com.learn.tdd.controller.response.ContentListResponse;
import com.learn.tdd.service.NotificationService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import javax.validation.Valid;
import java.util.List;


@RestController
@RequestMapping("/notify")
@AllArgsConstructor
public class NotifyController {

    private NotificationService notificationService;

    @PostMapping
    public ResponseEntity<String> notify(@RequestBody @Valid NotificationRequest notification) {
        String message =  notificationService.notify(notification.getUserId(),notification.getContent(),notification.getStatus());

        if (message.equals(notificationService.SUCCESS)) {
            return ResponseEntity.ok(notificationService.SUCCESS);
        }
        return ResponseEntity.badRequest().body(message);
    }
    @PostMapping("/update")
    public ResponseEntity<String> update(@RequestBody @Valid NotificationRequest notification) {
        String message =  notificationService.updateNotify(notification.getUserId(),notification.getContent(),notification.getStatus());

        if (message.equals(notificationService.SUCCESS)) {
            return ResponseEntity.ok(notificationService.SUCCESS);
        }
        return ResponseEntity.badRequest().body(message);
    }
    @PostMapping("/search")
    public ResponseEntity<ContentListResponse> search(@RequestBody @Valid NotificationSearchRequest notificationSearchRequest ) {
        List<String> contentList = notificationService.searchNotify(notificationSearchRequest.getUserId());
        ContentListResponse contentListResponse = new ContentListResponse();
        contentListResponse.setContentList(contentList);

        return ResponseEntity.ok().body(contentListResponse);

    }


}
