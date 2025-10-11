package io.bootify.my_app.model;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class ChatMessage {
    private String sender;
    private String content;
    private String workOrder;
    private MessageType type;

    public enum MessageType {
        CHAT, JOIN, LEAVE
    }

    // getters and setters
}

