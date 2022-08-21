package com.learn.tdd.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.UUID;

@Entity
@Table(name = "notifications")
@Getter
@Setter
@NoArgsConstructor
public class Notification {
    @Id
    private String id;
    @Column(name = "user_id")

    private String userId;
    @Column(name = "content")
    private String content;
    @Column(name = "flag")
    private String status;

    public Notification(String userId,String content, String status) {
        this.id = UUID.randomUUID().toString();
        this.userId = userId;
        this.content = content;
        this.status = status;
    }
}
