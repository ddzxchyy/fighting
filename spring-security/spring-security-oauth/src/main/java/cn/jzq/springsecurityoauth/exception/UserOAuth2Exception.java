package cn.jzq.springsecurityoauth.exception;


import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import org.springframework.security.oauth2.common.exceptions.OAuth2Exception;


@JsonSerialize(using = UserOAuth2ExceptionSerializer.class)
public class UserOAuth2Exception extends OAuth2Exception {
    private Integer status = 400;

    public UserOAuth2Exception(String message, Throwable t) {
        super(message, t);
        status = ((OAuth2Exception)t).getHttpErrorCode();
    }

    @Override
    public int getHttpErrorCode() {
        return status;
    }

}
