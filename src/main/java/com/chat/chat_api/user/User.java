package com.chat.chat_api.user;

import java.util.ArrayList;
import java.util.List;

import com.chat.chat_api.chatroom.Chatroom;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "username", nullable = false, unique = true)
    private String username;
    
    @ManyToMany
    @JoinTable(
        name = "user_chatroom",
        joinColumns = @JoinColumn(name = "user_id"),
        inverseJoinColumns = @JoinColumn(name = "chatroom_id")
    )
    private List<Chatroom> chats = new ArrayList<>();

    public User(){}

    public User(String username){
        this.username = username; 
    }

    public String getUsername(){ return username; }
    public List<Chatroom> getChat(){ return chats; }
}
