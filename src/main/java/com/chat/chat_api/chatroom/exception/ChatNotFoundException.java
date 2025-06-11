package com.chat.chat_api.chatroom.exception;

public class ChatNotFoundException extends RuntimeException {
   public ChatNotFoundException(Long id){
    super("No results found for the chat with id: " + id);
   } 
}
