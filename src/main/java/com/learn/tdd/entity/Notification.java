package com.learn.tdd.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "notifications")
@Getter
@Setter
@NoArgsConstructor
public class Notification {
    @Id
    private String id;
    @Column(name = "content")
    private String content;
    @Column(name = "status")
    private String status;

    public Notification(String id, String content, String status) {
        this.id = id;
        this.content = content;
        this.status = status;
    }
}
