package com.learn.tdd.service;

import com.learn.tdd.entity.Notification;
import com.learn.tdd.exception.NotificationNotFindException;
import com.learn.tdd.repository.AccountRepository;
import com.learn.tdd.repository.NotificationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class NotificationService {
    private final NotificationRepository notificationRepository;
    private final AccountRepository accountRepository;
    public final String SUCCESS = "SUCCESS";

    public String notify(String userId, String content, String status) {
        if (accountRepository.findById(userId).isEmpty()) {
            throw new RuntimeException("用户不存在");

        }

        if (content.trim().isEmpty()) {
            throw new RuntimeException("内容为空");

        }

        final Notification notification = new Notification(userId, content, status);
        notificationRepository.save(notification);

        return SUCCESS;
    }

    public String updateNotify(String userId, String content, String status) {
        Optional<Notification> notificationToChange = notificationRepository.findByUserIdAndContent(userId, content);
        if (notificationToChange.isEmpty()) {
            throw new RuntimeException("该条通知不存在");
        }

        notificationToChange.get().setStatus(status);
        notificationRepository.save(notificationToChange.get());
        return SUCCESS;
    }

    public List searchNotify(String userId) {
        if (accountRepository.findById(userId).isEmpty()) {
            throw new RuntimeException("用户不存在");

        }
        List<Notification> notification = notificationRepository.findAllByUserId("id");

        if (notification.size() == 0) {
            throw new NotificationNotFindException("没有消息");
        }

        return notification.stream().map(Notification::getContent).collect(Collectors.toList());
    }
}
