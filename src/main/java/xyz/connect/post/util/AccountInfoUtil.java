package xyz.connect.post.util;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Component;

// Spring Cloud Gateway 에서 전달받은 계정 정보에 접근하는 유틸 클래스
@Component
public class AccountInfoUtil {

    public long getAccountId(HttpServletRequest request) {
        return Long.parseLong(request.getHeader("User-Pk"));
    }
}
