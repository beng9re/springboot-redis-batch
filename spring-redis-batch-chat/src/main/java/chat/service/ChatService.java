package chat.service;

import chat.domain.Chat;
import chat.domain.ChatPostDto;

import java.util.List;

public interface ChatService {

    void chat(ChatPostDto dto);
    List<Chat> totalChat();
}
