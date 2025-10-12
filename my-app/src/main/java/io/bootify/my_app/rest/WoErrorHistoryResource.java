package io.bootify.my_app.rest;

import io.bootify.my_app.model.ChatMessage;
import io.bootify.my_app.service.WoErrorHistoryService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/api/woErrorHistory", produces = MediaType.APPLICATION_JSON_VALUE)
public class WoErrorHistoryResource {
    @Autowired
    WoErrorHistoryService woErrorHistoryService;
    @GetMapping("/history/workOrder/{workOrder}")
    public ResponseEntity<List<ChatMessage>> getAllDetailErrorsByWorkOrder(@PathVariable(name = "workOrder") final String workOrder) {
        return ResponseEntity.ok(woErrorHistoryService.findByWorkOrder(workOrder));
    }
    @PostMapping("/history/update")
    public ResponseEntity<Void> updateDetailErrors(@RequestBody @Valid final ChatMessage message) {
        woErrorHistoryService.updateError(message);
        return ResponseEntity.ok().build();
    }
}
