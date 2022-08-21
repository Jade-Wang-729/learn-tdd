package com.learn.tdd.service;

import com.learn.tdd.entity.Notification;
import com.learn.tdd.repository.AccountRepository;
import com.learn.tdd.repository.NotificationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

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

    public String updateNotify(String id, String content, String status) {
        Optional<Notification> notificationToChange = notificationRepository.findByIdAndContent(id, content);
        if (notificationToChange.isEmpty()) {
            return "该条通知不存在";
        }

        notificationToChange.get().setStatus(status);
        notificationRepository.save(notificationToChange.get());
        return SUCCESS;
    }

}
