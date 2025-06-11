package com.chat.chat_api.chatroom;

import java.util.ArrayList;
import java.util.List;

import com.chat.chat_api.message.Message;
import com.chat.chat_api.user.User;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "chatrooms")
public class Chatroom {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "chat_name", nullable = false)
    private String name;

    @ManyToMany(mappedBy = "chats")
    private List<User> chatUsers = new ArrayList<>();

    @OneToMany(mappedBy = "chat")
    private List<Message> messages = new ArrayList<>();

    public Chatroom(){}
    
    public Chatroom(final String name){
        this.name = name;
    }

    public Chatroom(final String name, final Long id){
        this.name = name;
        this.id = id;
    }

    public void setName(final String name) {
        this.name = name;
    }

    public void addUser(final User user) {
        chatUsers.add(user);
    }

    public void addMessages(final Message message) {
        messages.add(message);
    }

    public Long getId() {
        return id;
    }
    public String getName() { return name; }
    public List<User> getChatUsers() { return chatUsers; }
    public List<Message> getMessages() { return messages; }
}
