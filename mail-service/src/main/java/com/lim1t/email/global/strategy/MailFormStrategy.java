package com.lim1t.email.global.strategy;

import com.lim1t.email.domain.mail.MailPurpose;
import org.springframework.mail.SimpleMailMessage;

public interface MailFormStrategy {
    SimpleMailMessage createMailForm(String receiver);
    MailPurpose getPurpose();
}