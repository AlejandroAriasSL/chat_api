package com.chat.chat_api.chatroom;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1/chats")
public class ChatRestController {

    private final ChatService chatService;

    public ChatRestController(ChatService chatService){
        this.chatService = chatService;
    }

    @PostMapping
    public ResponseEntity<Chatroom> createChat(@RequestBody CreateChat chat){
        return ResponseEntity.ok(chatService.createOrUpdate(chat)); 
    }

    @GetMapping
    public ResponseEntity<List<ChatDTO>> getAllChats(){
        return ResponseEntity.ok(chatService.getAll());
    }
}
