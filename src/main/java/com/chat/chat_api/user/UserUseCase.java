package com.chat.chat_api.user;

import com.chat.chat_api.usecase.UseCase;
import com.chat.chat_api.user.dto.UserRecord;

public interface UserUseCase <R extends UserRecord, S> extends UseCase<S, R> {
    
}