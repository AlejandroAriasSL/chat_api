package com.chat.chat_api.role;

import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends  JpaRepository<Long, Role> {
    
}
