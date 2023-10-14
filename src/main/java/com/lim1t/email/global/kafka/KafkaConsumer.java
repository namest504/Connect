package com.lim1t.email.global.kafka;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.lim1t.email.domain.mail.MailService;
import com.lim1t.email.domain.mail.dto.MailRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class KafkaConsumer {

    private final MailService mailService;
    private final ObjectMapper objectMapper;

    public KafkaConsumer(MailService mailService, ObjectMapper objectMapper) {
        this.mailService = mailService;
        this.objectMapper = objectMapper;
    }

    @KafkaListener(topics = "mail")
    public void consume(String value) throws Exception {
        log.info("mail kafkaListener 동작");
        MailRequest request = objectMapper.readValue(value, MailRequest.class);
        log.info("value = [{}]", value);
        mailService.sendEmail(request.receiverEmail(), request.purpose());
    }
}
