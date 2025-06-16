package com.chat.chat_api.role;

import org.springframework.stereotype.Service;

@Service
public class RoleService {
    
    private final RoleRepository repository;

    public RoleService(RoleRepository roleRepository){
        this.repository = roleRepository;
    }

    public Role getDefaultRole(){
        return repository.findByRoleName("USER")
                         .orElseThrow(() -> new RuntimeException("Role with name '%s' not found".formatted("USER")));
    }
}
