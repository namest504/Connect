package xyz.connect.post.filter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.constraints.NotNull;
import java.io.IOException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import xyz.connect.post.custom_exception.PostApiException;
import xyz.connect.post.enumeration.ErrorCode;

@Component
@Slf4j
@RequiredArgsConstructor
public class AclFilter extends OncePerRequestFilter {

    @Value("${gateway-host}")
    private String gatewayHost;

    @Override
    protected void doFilterInternal(HttpServletRequest request,
            @NotNull HttpServletResponse response,
            @NotNull FilterChain filterChain) throws ServletException, IOException {
        log.info("Request about " + request.getRemoteAddr() + " -> " + request.getMethod() + " "
                + request.getRequestURL());
        if (!request.getRemoteAddr().equals(gatewayHost)) {
            log.warn("Requests from unknown ip. Current Gateway: " + gatewayHost);
            throw new PostApiException(ErrorCode.NOT_ALLOWED_ACCESS);
        }
        filterChain.doFilter(request, response);
    }
}
