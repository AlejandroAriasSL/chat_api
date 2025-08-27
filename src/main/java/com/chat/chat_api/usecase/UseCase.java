package com.chat.chat_api.usecase;

import com.chat.chat_api.dto.DTO;

public interface UseCase <S ,R extends DTO> {
    S execute(R record);
}