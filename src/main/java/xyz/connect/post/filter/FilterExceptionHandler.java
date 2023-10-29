package xyz.connect.post.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.FilterChain;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.constraints.NotNull;
import java.io.IOException;
import java.io.PrintWriter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import xyz.connect.post.custom_exception.PostApiException;
import xyz.connect.post.enumeration.ErrorCode;
import xyz.connect.post.web.model.response.ErrorResponse;

@Component
@Slf4j
@RequiredArgsConstructor
public class FilterExceptionHandler extends OncePerRequestFilter {

    private final ObjectMapper objectMapper;

    @Override
    protected void doFilterInternal(@NotNull HttpServletRequest request,
            @NotNull HttpServletResponse response,
            @NotNull FilterChain filterChain) throws IOException {
        try {
            filterChain.doFilter(request, response);
        } catch (PostApiException e) {
            response(e.getErrorCode(), response);
        } catch (Exception e) {
            log.warn("Unexpected filter exception");
        }
    }

    private void response(ErrorCode errorCode, HttpServletResponse response) throws IOException {
        log.info("[Filter exception] " + errorCode.getMessage());
        ErrorResponse errorResponse = makeErrorResponse(errorCode);
        String responseJsonString = objectMapper.writeValueAsString(errorResponse);
        response.setContentType("application/json; charset=UTF-8");
        response.setStatus(errorCode.getStatus().value());
        PrintWriter writer = response.getWriter();

        writer.print(responseJsonString);
        writer.flush();
        writer.close();
    }

    private ErrorResponse makeErrorResponse(ErrorCode errorCode) {
        return new ErrorResponse(
                errorCode.getStatus().toString(),
                errorCode.getMessage(),
                errorCode.getCode()
        );
    }
}
