package xyz.connect.user.web.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.NonNull;

@Builder
public record MailRequest(
        @JsonProperty("receiver_email")
        @Email
        @NotBlank
        String receiverEmail,

        @JsonProperty("purpose")
        @NotBlank
        String purpose
) {}
