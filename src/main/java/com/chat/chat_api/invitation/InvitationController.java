package com.chat.chat_api.invitation;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

@Controller
public class InvitationController {

    private final SimpMessagingTemplate invitationTemplate;
    private final InvitationService invitationService;

    public InvitationController(SimpMessagingTemplate invitationTemplate, InvitationService invitationService){
        this.invitationTemplate = invitationTemplate;
        this.invitationService = invitationService;
    }

    @MessageMapping("/chat/invite")
    public void invite(InvitationDTO invitationDTO){
        invitationService.sendInvitation(invitationDTO);
        invitationTemplate.convertAndSendToUser(
            invitationDTO.recieverUsername(), 
            "queue/invitations",
            invitationDTO
        );

        System.out.println("Enviando invitación a: " + invitationDTO.recieverUsername());
    }
}
