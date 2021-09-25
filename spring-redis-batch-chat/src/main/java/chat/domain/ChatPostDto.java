package chat.domain;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ChatPostDto {

    private String message;
    private String user;

    public Chat getChatEntity(){
        return new Chat.ChatBuilder().message(this.message).user(this.user).build();

    }

}
