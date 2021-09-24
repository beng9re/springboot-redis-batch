package chat.service;

import chat.domain.ChatPostDto;
import com.fasterxml.jackson.core.JsonProcessingException;

public interface ChatService {

    public void chat(ChatPostDto dto);
}
