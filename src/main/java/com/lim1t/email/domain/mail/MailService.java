package com.lim1t.email.domain.mail;

import com.lim1t.email.global.exception.ErrorCode;
import com.lim1t.email.global.exception.MailApiException;
import com.lim1t.email.global.strategy.MailFormStrategy;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
public class MailService {

    private final JavaMailSender emailSender;
    private final MailRedisRepository mailRedisRepository;
    private final Map<MailPurpose, MailFormStrategy> strategies;

    public MailService(JavaMailSender emailSender, MailRedisRepository mailRedisRepository) {
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
                            requestAuthAPI(mail);
                            mailRedisRepository.delete(mail);
                            },
                        () ->
                                new MailApiException(ErrorCode.NO_CERTIFICATION)
                );
    }

    private void requestAuthAPI(final Mail mail) {
        // todo : 인증 완료된 유저 정보 수정 API (인증서버)
    }
}
