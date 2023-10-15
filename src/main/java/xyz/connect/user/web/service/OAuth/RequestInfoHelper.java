package xyz.connect.user.web.service.OAuth;

import xyz.connect.user.web.dto.params.KaKaoInfoParam;

public interface RequestInfoHelper {

    String requestAccessToken(String code);

    KaKaoInfoParam requestInfo(String accessToken);

    KaKaoInfoParam request(String code);

}
