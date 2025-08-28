package com.chat.chat_api.invitation;

import jakarta.persistence.*;
import java.time.LocalDateTime;

import com.chat.chat_api.chatroom.Chatroom;
import com.chat.chat_api.user.User;

@Entity
@Table(name = "invitations")
public class Invitation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "chat_id", nullable = false)
    private Chatroom chat;

    @ManyToOne
    @JoinColumn(name = "sender_id", nullable = false)
    private User sender;

    @ManyToOne
    @JoinColumn(name = "reciever_id", nullable = false)
    private User reciever;

    @Enumerated(EnumType.STRING)
    private Status status = Status.PENDING;

    private LocalDateTime sentAt = LocalDateTime.now();

    public enum Status {
        PENDING,
        ACCEPTED,
        REJECTED
    }

    public Long getId() {
        return id;
    }

    public Chatroom getChat() {
        return chat;
    }

    public User getReciever() {
        return reciever;
    }

    public User getSender() {
        return sender;
    }

    public LocalDateTime getSentAt() {
        return sentAt;
    }

    public Status getStatus() {
        return status;
    }

    public void setChat(Chatroom chat) {
        this.chat = chat;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setReciever(User reciever) {
        this.reciever = reciever;
    }

    public void setSender(User sender) {
        this.sender = sender;
    }

    public void setSentAt(LocalDateTime sentAt) {
        this.sentAt = sentAt;
    }

    public void setStatus(Status status) {
        this.status = status;
    }
}