package io.bootify.my_app.service;

import io.bootify.my_app.domain.WoErrorHistory;
import io.bootify.my_app.model.ChatMessage;
import io.bootify.my_app.repos.WoErrorHistoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Date;
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
        woErrorHistory.setTime(LocalDateTime.now());
        return Math.toIntExact(woErrorHistoryRepository.save(woErrorHistory).getId());
    }
    public List<WoErrorHistory> findByWorkOrder(String workOrder) {
        return woErrorHistoryRepository.findByWorkOrder(workOrder);
    }
    public void updateError(ChatMessage message) {
        WoErrorHistory woErrorHistory = new WoErrorHistory();
        woErrorHistory.setSender(message.getSender());
        woErrorHistory.setContent(message.getContent());
        woErrorHistory.setWorkOrder(message.getWorkOrder());
        woErrorHistory.setType(message.getType().name());
        woErrorHistory.setStatus(message.getStatus());
        woErrorHistory.setId(Long.valueOf(message.getId()));
        woErrorHistoryRepository.save(woErrorHistory);
    }
    @Transactional
    public void updateErrors(List<ChatMessage> messages) {
        for (ChatMessage message : messages){
            WoErrorHistory woErrorHistory = new WoErrorHistory();
            woErrorHistory.setSender(message.getSender());
            woErrorHistory.setContent(message.getContent());
            woErrorHistory.setWorkOrder(message.getWorkOrder());
            woErrorHistory.setType(message.getType().name());
            woErrorHistory.setStatus(message.getStatus());
            woErrorHistory.setId(Long.valueOf(message.getId()));
            woErrorHistoryRepository.save(woErrorHistory);
        }
    }
}
