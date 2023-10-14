package com.lim1t.email.global.strategy;

import com.lim1t.email.domain.mail.MailPurpose;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Component;

@Component
public class WelcomeMailFormStrategy implements MailFormStrategy {

    private final MailPurpose purpose;

    public WelcomeMailFormStrategy() {
        this.purpose = MailPurpose.WELCOME;
    }

    @Override
    public SimpleMailMessage createMailForm(String receiver) {
        SimpleMailMessage message = new SimpleMailMessage();

        message.setTo(receiver);
        message.setSubject("Connect 가입을 환영합니다!");

        String mailText = receiver +
                        "님 Connect " +
                        "가입을 환영합니다.";

        message.setText(mailText);
        return message;
    }

    @Override
    public MailPurpose getPurpose() {
        return this.purpose;
    }
}
