package antd_access.config;

import antd_access.model.db.UserEntity;
import antd_access.services.AntdUserDetailsService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.RememberMeAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.RememberMeServices;
import org.springframework.stereotype.Component;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Arrays;

@Slf4j
@Component
public class CustomRememberMeServices implements RememberMeServices {

    private final AntdUserDetailsService userDetailsService ;

    public CustomRememberMeServices(AntdUserDetailsService userDetailsService) {
        this.userDetailsService = userDetailsService;
    }

    @Override
    public Authentication autoLogin(HttpServletRequest request, HttpServletResponse httpServletResponse) {
        Arrays.stream(request.getCookies())
                .forEach(c -> log.info("cookie name : {} , value : {}", c.getName(),c.getValue()) );

        Cookie tokenCookie = Arrays.stream(request.getCookies()).filter(c -> "token".equals(c.getName())).findFirst().orElseThrow(() -> new RuntimeException("not login!"));
        // token 中的value 结构为: token:lastLoginAt

        String[] token = tokenCookie.getValue().split(":");
        if(token.length != 2) {
            throw new RuntimeException("token error");
        }

        UserEntity userEntity = userDetailsService.fetchUserByTokenAndLoginTime(token[0], Long.parseLong(token[1]));

        if(userEntity == null) {
            throw new RuntimeException("token is invalid");
        }

        return new RememberMeAuthenticationToken("remember-me-key",userEntity, userEntity.getAuthorities());
    }

    @Override
    public void loginFail(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
        log.info("login fail");
    }

    @Override
    public void loginSuccess(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Authentication authentication) {
        log.info("login success");
    }
}
