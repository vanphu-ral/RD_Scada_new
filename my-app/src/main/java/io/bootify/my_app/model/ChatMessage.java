package io.bootify.my_app.model;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class ChatMessage {
    private Integer id;
    private String sender;
    private String content;
    private String workOrder;
    private MessageType type;
    private Integer status; // 0: unread, 1: read

    public enum MessageType {
        CHAT, JOIN, LEAVE
    }

    // getters and setters
}

