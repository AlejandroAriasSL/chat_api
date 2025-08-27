package com.chat.chat_api.registry;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.chat.chat_api.annotation.UseCaseType;
import com.chat.chat_api.dto.DTO;
import com.chat.chat_api.user.UserUseCase;
import com.chat.chat_api.user.dto.UserRecord;

@Component
public class UserUseCaseRegistry {

    private final Map<Class<? extends UserRecord>, UserUseCase<?, ?>> registry = new HashMap<>();

    @Autowired
    public UserUseCaseRegistry(Collection<UserUseCase<?, ?>> useCases) {
        for(UserUseCase<?, ?> useCase : useCases){
            Class<? extends UserRecord> requestType = extractRequestType(useCase);
            registry.put(requestType, useCase);
        }
    }

    @SuppressWarnings("unchecked")
    public <R extends UserRecord, S> UserUseCase<R, S> get(Class<R> requestType){
        UserUseCase<?, ?> useCase = registry.get(requestType);

        if (useCase == null){
            throw new IllegalStateException("No use case registered for request ype %s".formatted(requestType.getName()));
        }

        return (UserUseCase<R, S>) useCase;
    }

    @SuppressWarnings("unchecked")
    public Class<? extends UserRecord> extractRequestType(UserUseCase<?, ?> useCase){
        UseCaseType annotation = useCase.getClass().getAnnotation(UseCaseType.class);

        if (annotation == null){
            throw new IllegalStateException("Missing value of @UseCaseType on %s".formatted(useCase.getClass().getSimpleName()));
        }

        Class<? extends DTO> value = annotation.value();

        return (Class<? extends UserRecord>) value;
    } 

    public Map<Class<? extends UserRecord>, UserUseCase<?, ?>> getRegistry() {
        return registry;
    }

}
