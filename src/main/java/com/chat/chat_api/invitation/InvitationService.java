package com.chat.chat_api.invitation;

import org.springframework.stereotype.Service;

import com.chat.chat_api.chatroom.ChatService;
import com.chat.chat_api.chatroom.Chatroom;
import com.chat.chat_api.user.User;
import com.chat.chat_api.user.UserService;

@Service
public class InvitationService {

    private final UserService userService;
    private final ChatService chatService;
    private final InvitationRepository repository;
    
    public InvitationService(UserService userService, ChatService chatService, InvitationRepository repository){
        this.userService = userService;
        this.chatService = chatService;
        this.repository = repository;
    }

    public void sendInvitation(InvitationDTO invitationDTO){
        User reciever = userService.findByUsername(invitationDTO.recieverUsername()).orElseThrow();
        User sender = userService.findByUsername(invitationDTO.senderUsername()).orElseThrow();
        Chatroom chat = chatService.getById(invitationDTO.chatId());

        Invitation invitation = new Invitation();
        invitation.setSender(sender);
        invitation.setReciever(reciever);
        invitation.setChat(chat);
        repository.save(invitation);
    } 
}
