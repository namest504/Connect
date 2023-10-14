package com.lim1t.email.domain.mail.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;

@Builder
public record MailRequest(
        @JsonProperty("receiver_email")
        String receiverEmail,
        @JsonProperty("purpose")
        String purpose
) {}
