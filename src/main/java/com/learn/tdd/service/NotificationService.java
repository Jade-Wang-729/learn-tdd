package com.learn.tdd.service;

import com.learn.tdd.entity.Notification;
import com.learn.tdd.repository.AccountRepository;
import com.learn.tdd.repository.NotificationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class NotificationService {
    private final NotificationRepository notificationRepository;
    private final AccountRepository accountRepository;
    public final String SUCCESS = "SUCCESS";

    public String notify(String userId, String content, String status) {
        if (accountRepository.findById(userId).isEmpty()) {
            return "用户不存在";
        }

        if (content.trim().isEmpty()) {
            return "内容为空";
        }

        final Notification notification = new Notification(userId, content, status);
        notificationRepository.save(notification);

        return SUCCESS;
    }

    public String updateNotify(String userId, String content, String status) {
        Optional<Notification> notificationToChange = notificationRepository.findByUserIdAndContent(userId, content);
        if (notificationToChange.isEmpty()) {
            return "该条通知不存在";
        }

        notificationToChange.get().setStatus(status);
        notificationRepository.save(notificationToChange.get());
        return SUCCESS;
    }

}
