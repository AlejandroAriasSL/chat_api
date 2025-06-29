package com.chat.chat_api.user;

import org.springframework.stereotype.Component;

import com.chat.chat_api.registry.UserUseCaseRegistry;
import com.chat.chat_api.user.dto.UserRecord;

@Component
public class UserFacade {

    private final UserUseCaseRegistry registry;

    public UserFacade(UserUseCaseRegistry registry){
        this.registry = registry;
    }

    @SuppressWarnings("unchecked")
    public <R extends UserRecord, S> S execute(R request){
        Class<R> requestType = (Class<R>) request.getClass();
        UserUseCase<R, S> useCase = registry.get(requestType);
        return useCase.execute(request);
    }
}
