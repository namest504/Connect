package com.lim1t.email.domain.mail;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/mail")
public class MailController {

    private final MailService mailService;

    public MailController(MailService mailService) {
        this.mailService = mailService;
    }

    @GetMapping("/confirm/{certification-number}")
    public ResponseEntity<String> confirmMail(@PathVariable("certification-number") String cert) {
        mailService.confirmAuth(cert);
        return ResponseEntity.ok()
                .body("인증이 완료되셨습니다.");
    }
}
