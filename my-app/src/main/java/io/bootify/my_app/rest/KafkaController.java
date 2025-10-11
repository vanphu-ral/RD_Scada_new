package io.bootify.my_app.rest;


import io.bootify.my_app.model.ChatMessage;
import io.bootify.my_app.service.KafkaProducer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/kafka")
public class KafkaController {
    @Autowired
    private KafkaProducer producer;

    @PostMapping("/publish")
    public ResponseEntity<String> publish(@RequestParam String message) {
        producer.sendMessage("scada-giam-sat", message);
        return ResponseEntity.ok("Message sent to Kafka");
    }
}
