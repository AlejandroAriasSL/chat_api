package com.chat.chat_api.chatroom;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.chat.chat_api.user.User;
import com.chat.chat_api.user.UserService;

@Service
public class ChatService {

    private final ChatroomRepository repository;
    private final UserService userService;

    public ChatService(final ChatroomRepository repository, final UserService userService){
        this.repository = repository;
        this.userService = userService;
    }

    @Transactional
    public Chatroom createOrUpdate(Chatroom chat){
        return repository.save(chat);
    }

    public Chatroom getById(Long id) throws RuntimeException {
        return repository.findById(id).orElseThrow(() -> new RuntimeException("Chat not found!"));
    }

    public List<Chatroom> getAll(){
        return repository.findAll();
    }

    @Transactional
    public void deleteById(Long id) throws RuntimeException{
        if (!repository.existsById(id)){
            throw new RuntimeException("Chat doesn't exist!");
        }
        repository.deleteById(id);
    }

    @Transactional
    public Chatroom addParticipant(Long chatroomId, Long userId) throws RuntimeException {
        Chatroom chatroom = getById(chatroomId);
        User user = userService.getById(userId);

        chatroom.addUser(user);
        return repository.save(chatroom);
    }
}
