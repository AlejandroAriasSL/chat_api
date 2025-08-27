package com.chat.chat_api.user;

import org.springframework.stereotype.Component;

import com.chat.chat_api.registry.UserUseCaseRegistry;
import com.chat.chat_api.user.dto.UserRecord;

@Component
public class UserFacade {

    private final UserUseCaseRegistry registry;

    public UserFacade(UserUseCaseRegistry registry) {
        this.registry = registry;
    }

    @SuppressWarnings("unchecked")
    public <R extends UserRecord, S> S execute(R request) {
        Class<?> requestClass = request.getClass();

        UserUseCase<R, S> useCase = (UserUseCase<R, S>) registry.getRegistry()
            .entrySet()
            .stream()
            .filter(entry -> entry.getKey().isAssignableFrom(requestClass))
            .map(entry -> entry.getValue())
            .findFirst()
            .orElseThrow(() -> new IllegalStateException(
                "No use case registered for request type " + requestClass.getName()
            ));

        return useCase.execute(request);
    }
}
