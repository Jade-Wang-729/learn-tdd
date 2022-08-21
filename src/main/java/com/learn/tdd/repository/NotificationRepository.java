package com.learn.tdd.repository;

import com.learn.tdd.entity.Notification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface NotificationRepository extends JpaRepository<Notification, String> {
    Optional<Notification> findById(String id);
    Optional<Notification> findByIdAndContent(String id, String content);
}
