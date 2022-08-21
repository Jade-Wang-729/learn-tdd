package com.learn.tdd.service;

import com.learn.tdd.entity.Notification;
import com.learn.tdd.repository.AccountRepository;
import com.learn.tdd.repository.NotificationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class NotificationService {
    private final NotificationRepository notificationRepository;
    private final AccountRepository accountRepository;
    public final String SUCCESS = "SUCCESS";

    public String notify(String id, String content, String status) {
        if (accountRepository.findById(id).isEmpty()) {
            return "用户不存在";
        }

        if (content.trim().isEmpty()) {
            return "内容为空";
        }

        final Notification notification = new Notification(id, content, status);
        notificationRepository.save(notification);

        return SUCCESS;
    }
}
