package io.bootify.my_app.service;

import io.bootify.my_app.domain.WoErrorHistory;
import io.bootify.my_app.model.ChatMessage;
import io.bootify.my_app.repos.WoErrorHistoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class WoErrorHistoryService {
    @Autowired
    WoErrorHistoryRepository woErrorHistoryRepository;
    @Transactional
    public Integer insertError(ChatMessage message) {
        WoErrorHistory woErrorHistory = new WoErrorHistory();
        woErrorHistory.setSender(message.getSender());
        woErrorHistory.setContent(message.getContent());
        woErrorHistory.setWorkOrder(message.getWorkOrder());
        woErrorHistory.setType(message.getType().name());
        woErrorHistory.setStatus(0);
        return Math.toIntExact(woErrorHistoryRepository.save(woErrorHistory).getId());
    }
    public List<ChatMessage> findByWorkOrder(String workOrder) {
        return woErrorHistoryRepository.findByWorkOrder(workOrder);
    }
    @Transactional
    public void updateError(ChatMessage message) {
        woErrorHistoryRepository.updateWoErrorHistory(message.getSender(), message.getContent(), message.getWorkOrder(), message.getType().name(), message.getStatus());
    }
    @Transactional
    public void updateErrors(List<ChatMessage> messages) {
        for (ChatMessage message : messages){
        woErrorHistoryRepository.updateWoErrorHistory(message.getSender(), message.getContent(), message.getWorkOrder(), message.getType().name(), message.getStatus());
        }
    }
}
