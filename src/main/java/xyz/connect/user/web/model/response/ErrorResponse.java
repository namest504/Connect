package xyz.connect.user.web.model.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ErrorResponse {

    private String status;
    private String message;
    private String code;
}