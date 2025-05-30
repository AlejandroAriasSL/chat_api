package com.chat.chat_api;

import java.util.ArrayList;
import java.util.List;

public class Chatroom {
    
    private String name;
    private List<User> chatUsers = new ArrayList<>();
    private List<Message> messages = new ArrayList<>();
    
    public Chatroom(final String name){
        this.name = name;
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

    
    public String getName() { return name; }
    public List<User> getChatUsers() { return chatUsers; }
    public List<Message> getMessages() { return messages; }
}
