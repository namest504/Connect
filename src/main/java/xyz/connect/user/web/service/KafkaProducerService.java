package xyz.connect.user.web.service;

import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import xyz.connect.user.web.dto.request.MailRequest;

@Service
public class KafkaProducerService {

    private final KafkaTemplate<String, Object> kafkaTemplate;

    private static final String MAIL_TOPIC = "mail";

    public KafkaProducerService(KafkaTemplate<String, Object> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void sendMessage(MailRequest request) {
        kafkaTemplate.send(MAIL_TOPIC, request);
    }
}
