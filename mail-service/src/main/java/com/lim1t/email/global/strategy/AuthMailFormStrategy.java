package com.lim1t.email.global.strategy;

import com.lim1t.email.domain.mail.Mail;
import com.lim1t.email.domain.mail.MailPurpose;
import com.lim1t.email.domain.mail.MailRedisRepository;
import com.lim1t.email.global.exception.ErrorCode;
import com.lim1t.email.global.exception.MailApiException;
import com.lim1t.email.global.util.RandomNumberGenerator;
import java.util.List;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Component;

@Component
public class AuthMailFormStrategy implements MailFormStrategy {

    private final RandomNumberGenerator numberGenerator;
    private final MailRedisRepository mailRedisRepository;
    private final DiscoveryClient discoveryClient;
    private final MailPurpose purpose;

    public AuthMailFormStrategy(RandomNumberGenerator numberGenerator,
            MailRedisRepository mailRedisRepository, DiscoveryClient discoveryClient) {
        this.numberGenerator = numberGenerator;
        this.mailRedisRepository = mailRedisRepository;
        this.discoveryClient = discoveryClient;
        this.purpose = MailPurpose.AUTH;
    }

    @Override
    public SimpleMailMessage createMailForm(String receiver) {
        String uuid = numberGenerator.createUUID();

        // todo : 이전에 보낸 인증 메일 처리 과정

        Mail save = mailRedisRepository.save(Mail.builder()
                .id(uuid)
                .email(receiver)
                .expiredTime(600)
                .build());

        List<ServiceInstance> instances = discoveryClient.getInstances("gateway-service");
        if (instances.isEmpty()) {
            throw new MailApiException(ErrorCode.UNABLE_ACCESS_SERVICE);
        }

        ServiceInstance instanceInfo = instances.get(0);
        String serviceUrl = instanceInfo.getUri().toString();

        SimpleMailMessage message = new SimpleMailMessage();

        message.setTo(receiver);
        message.setSubject("Connect 회원 인증 메일입니다.");

        String mailText =
                receiver + "님 Connect" +
                "회원 인증 메일입니다.\n" +
                serviceUrl + "/api/v1/mail/confirm/" + save.getId() +
                "해당 링크로 인증을 완료해주시길 바랍니다.";

        message.setText(mailText);
        return message;
    }

    @Override
    public MailPurpose getPurpose() {
        return this.purpose;
    }
}
