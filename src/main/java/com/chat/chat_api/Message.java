package com.chat.chat_api;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "messages")
public class Message {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "message_timestamp", nullable = false)
    private LocalDateTime timestamp; 

    @Column(name = "message_content", nullable = false)
    private String content;

    @ManyToOne
    @JoinColumn(name = "sender_id", nullable = false)
    private User sender;

    @ManyToOne
    @JoinColumn(name = "chatroom_id", nullable = false)
    private Chatroom chat;
    
    public Message(){}

    public Message(LocalDateTime timestamp, String content, User sender, Chatroom chat){
        this.timestamp = timestamp;
        this.content = content;
        this.sender = sender;
        this.chat = chat;
    }

    public Long getId() { return id; }
    public LocalDateTime getTimestamp() { return timestamp; }
    public String getContent() { return content; }
    public User getSender() { return sender; }
    public Chatroom getChat() { return chat; }

    public void setContent(String content) { this.content = content; }
    
}

