package com.chat.chat_api;

import java.util.List;

public class User {

    private String username;
    private List<Chatroom> chats;

    public User(String username){
        this.username = username;
    }

    public String getUsername(){
        return username;
    }

    public List<Chatroom> getChat(){
        return chats;
    }
}
