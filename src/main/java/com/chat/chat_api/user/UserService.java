package com.chat.chat_api.user;

import java.util.List;

import org.springframework.stereotype.Service;

import com.chat.chat_api.chatroom.ChatService;
import com.chat.chat_api.chatroom.Chatroom;
import com.chat.chat_api.chatroom.dto.ChatroomSummaryDTO;
import com.chat.chat_api.message.MessageResponseDTO;

import jakarta.transaction.Transactional;

@Service
public class UserService {

    private final UserRepository repository;
    private final ChatService chatService;

    public UserService(final UserRepository repository, final ChatService chatService){
        this.repository = repository;
        this.chatService = chatService;
    }

    @Transactional
    public User createOrUpdate(CreateUser request){
       return repository.save(new User(request.username())); 
    }

    public User getById(Long id) throws RuntimeException {
        return repository.findById(id)
                         .orElseThrow(() -> new RuntimeException("User not found!"));
    }

    public List<User> getAll(){
        return repository.findAll();
    }

    @Transactional
    public void deleteById(Long id) throws RuntimeException {
        if (!repository.existsById(id)){
            throw new RuntimeException("User doesn't exist!");
        }
        repository.deleteById(id);
    }

    @Transactional 
    public UserDTO addChatToUser(Long userId, Long chatId){
        User user = getById(userId);
        Chatroom chat = chatService.getById(chatId);

        List<Chatroom> userChats = user.getChats();
        if (!userChats.contains(chat)){
            userChats.add(chat);
        }

        User savedUser = repository.save(user);
        return UserDTO.toDto(savedUser);
    }

    public UserChatsDTO getUserChats(Long userId){
        User user = getById(userId);

        List<ChatroomSummaryDTO> userChats = user.getChats()
          .stream()
          .map(chat -> {
            List<MessageResponseDTO> messageResponseDTOs = chat.getMessages()
              .stream()
              .map(MessageResponseDTO::toDTO)
              .toList();
                                                        
            return new ChatroomSummaryDTO(chat.getId(), chat.getName(), messageResponseDTOs);
          }).toList();

        return new UserChatsDTO(userId, user.getUsername(), userChats);
    }
}
