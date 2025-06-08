package com.chat.chat_api.user;

import java.util.List;

import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;

@Service
public class UserService {

    private final UserRepository repository;

    public UserService(final UserRepository repository){
        this.repository = repository;
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
}
