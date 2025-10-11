package io.bootify.my_app.rest;

import io.bootify.my_app.model.ChatMessage;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

@Controller
public class ChatController {
    @MessageMapping("/chat.sendMessage") // client gửi đến /app/chat.sendMessage
    @SendTo("/topic/public") // server gửi lại cho tất cả client đang subscribe
    public ChatMessage sendMessage(ChatMessage message) {
        return message;
    }
}
