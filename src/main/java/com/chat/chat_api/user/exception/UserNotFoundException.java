package com.chat.chat_api.user.exception;

public class UserNotFoundException extends RuntimeException {
   public UserNotFoundException(Long id){
    super("No results found for the user with id: " + id);
   } 
}
