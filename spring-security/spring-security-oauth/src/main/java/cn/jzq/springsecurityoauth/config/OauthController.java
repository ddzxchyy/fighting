package cn.jzq.springsecurityoauth.config;

import cn.jzq.fightingcommon.uitls.R;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.common.DefaultOAuth2AccessToken;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.endpoint.TokenEndpoint;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.LinkedHashMap;
import java.util.Map;

@RestController
@RequestMapping("/oauth")
@AllArgsConstructor
public class OauthController {

    private final TokenEndpoint tokenEndpoint;

    @GetMapping("/token")
    public R getAccessToken(Principal principal, @RequestParam Map<String, String> parameters) throws HttpRequestMethodNotSupportedException {
        return getToken(tokenEndpoint.getAccessToken(principal, parameters).getBody());
    }

    @PostMapping("/token")
    public R postAccessToken(Principal principal, @RequestParam Map<String, String> parameters) throws HttpRequestMethodNotSupportedException {
        return getToken(tokenEndpoint.postAccessToken(principal, parameters).getBody());
    }

    //自定义返回格式
    private R getToken(OAuth2AccessToken accessToken) {
        DefaultOAuth2AccessToken token = (DefaultOAuth2AccessToken) accessToken;
        Map<String, Object> data = new LinkedHashMap<>(token.getAdditionalInformation());
        data.put("access_token", token.getValue());
        data.put("expires_in", token.getExpiresIn());
        if (token.getRefreshToken() != null) {
            data.put("refresh_token", token.getRefreshToken().getValue());
        }
        return R.ok(data);
    }

}