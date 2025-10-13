package io.bootify.my_app.service;

import io.bootify.my_app.model.ChatMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

@Service
public class KafkaProducer {
    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;
    @Autowired
    private SimpMessagingTemplate messagingTemplate;
    @Autowired
    WoErrorHistoryService woErrorHistoryService;
    public void sendMessage(String topic, String message) {
        kafkaTemplate.send(topic, message);
//        ChatMessage messages = new ChatMessage();
//        messages.setType(ChatMessage.MessageType.CHAT);
//        messages.setSender("Server");
//        messages.setContent(message);
//        messages.setWorkOrder("WO-146247-1");
//        messages.setStatus(0);
//        messages.setId(woErrorHistoryService.insertError(messages));
//        messagingTemplate.convertAndSend("/topic/public", messages);
    }
}
