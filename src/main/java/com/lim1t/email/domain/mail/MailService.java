package com.lim1t.email.domain.mail;

import com.lim1t.email.global.exception.ErrorCode;
import com.lim1t.email.global.exception.MailApiException;
import com.lim1t.email.global.strategy.MailFormStrategy;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

@Slf4j
@Service
public class MailService {

    private final RestTemplate restTemplate;
    private final JavaMailSender emailSender;
    private final MailRedisRepository mailRedisRepository;
    private final Map<MailPurpose, MailFormStrategy> strategies;

    private final String CLIENT_NAME_HEADER = "client-name";

    @Value("${spring.application.name}")
    private String CLIENT_NAME;

    public MailService(RestTemplate restTemplate, JavaMailSender emailSender, MailRedisRepository mailRedisRepository) {
        this.restTemplate = restTemplate;
        this.emailSender = emailSender;
        this.mailRedisRepository = mailRedisRepository;
        this.strategies = new HashMap<>();
    }

    @Autowired
    public void setStrategies(List<MailFormStrategy> strategyList) {
        for (MailFormStrategy strategy : strategyList) {
            this.strategies.put(strategy.getPurpose(), strategy);
        }
    }

    @Transactional
    public void sendEmail(final String receiver, final String purpose) {
        MailFormStrategy mailForm = strategies.get(MailPurpose.valueOf(purpose));
        SimpleMailMessage message = mailForm.createMailForm(receiver);

        try {
            emailSender.send(message);
            log.info("이메일 전송 완료 대상 : [{}]", receiver);

        } catch (RuntimeException e) {
            log.error("이메일 전송 실패", e);
            throw new MailApiException(ErrorCode.FAILED_SEND_MAIL);
        }
    }

    @Transactional
    public void confirmAuth(final String cert) {
        mailRedisRepository.findById(cert)
                .ifPresentOrElse(
                        mail -> {
                            requestAuthAPI(mail.getEmail());
                            mailRedisRepository.delete(mail);
                            },
                        () ->{
                            throw new MailApiException(ErrorCode.NO_CERTIFICATION);
                        }

                );
    }

    public void requestAuthAPI(final String mail) {
        HttpHeaders headers = new HttpHeaders();
        headers.set(CLIENT_NAME_HEADER, CLIENT_NAME);
        HttpEntity<String> request = new HttpEntity<>(mail, headers);

        ResponseEntity<String> response = restTemplate.exchange(
                "lb://user-service/api/v1/user/confirm/auth-mail",
                HttpMethod.POST,
                request,
                String.class
        );

        if (response.getStatusCode() == HttpStatus.OK) {
            log.info(response.getBody());
        } else {
            log.info("code : {}", response.getStatusCode());
        }
    }
}
