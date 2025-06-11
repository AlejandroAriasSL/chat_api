package com.chat.chat_api.chatroom;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.chat.chat_api.chatroom.dto.ChatDTO;
import com.chat.chat_api.chatroom.dto.CreateChatRequestDTO;
import com.chat.chat_api.chatroom.exception.ChatNotFoundException;

@Service
public class ChatService {

    private final ChatroomRepository repository;

    public ChatService(final ChatroomRepository repository){
        this.repository = repository;
    }

    @Transactional
    public Chatroom createOrUpdate(CreateChatRequestDTO chat){
        return repository.save(new Chatroom(chat.chatName()));
    }

    public Chatroom getById(Long id) throws ChatNotFoundException {
        return repository.findById(id).orElseThrow(() -> new ChatNotFoundException(id));
    }

    public List<ChatDTO> getAll(){
        return repository.findAll().stream()
                                   .map(ChatDTO::toDTO)
                                   .toList();
    }

    @Transactional
    public void deleteById(Long id) throws ChatNotFoundException {
        if (!repository.existsById(id)){
            throw new ChatNotFoundException(id);
        }
        repository.deleteById(id);
    }

}
