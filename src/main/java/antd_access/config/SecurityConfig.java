package antd_access.config;

import antd_access.model.db.UserEntity;
import antd_access.model.resp.HandlerResp;
import antd_access.services.AntdUserDetailsService;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Data;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.HttpStatusEntryPoint;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;
import java.util.Collections;

@Data
@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {


    final AntdUserDetailsService userDetailsService ;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
//        super.configure(http);

        http.authorizeRequests()
                .antMatchers("/api/v1/login", "/user/register","/user/current")
                .permitAll()

                .antMatchers("/swagger-ui/**", "/swagger-resources/**", "/v3/api-docs")
                .permitAll()
                .anyRequest()
                .authenticated()
                .and().httpBasic()
                .and()
                .formLogin()
                .loginProcessingUrl("/v1/login")

                .successHandler(new AuthenticationSuccessHandler() {
                    @Override
                    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
                        response.setContentType("application/json");
                        ObjectMapper mapper = new ObjectMapper();
                        UserEntity userEntity = (UserEntity) authentication.getPrincipal();

                        UserEntity resultUser =  userDetailsService.onLoginSuccess(userEntity.getUid(), request) ;

                        Cookie cookie = new Cookie("token", resultUser.getToken()+":"+resultUser.getLastLoginAt());
                        cookie.setMaxAge(60 * 60 * 24 * 7);
                        cookie.setPath("/");
                        response.addCookie(cookie);
                        mapper.createGenerator(response.getOutputStream())
                                .writeObject(HandlerResp.success("登陆成功!"));

                    }
                })
                .failureHandler(new AuthenticationFailureHandler() {
                    @Override
                    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {
                        response.setContentType("application/json");
                        ObjectMapper mapper = new ObjectMapper();
                        mapper.createGenerator(response.getOutputStream())
                                .writeObject(HandlerResp.failed("登陆失败!"));
                    }
                })
                .and()
                .logout()
                .deleteCookies("token", "JSESSIONID")
                .logoutUrl("/v1/logout")
                .and()
                .rememberMe()
                .rememberMeServices(new CustomRememberMeServices(userDetailsService))
                .key("remember-me-key")

                .and()
                .exceptionHandling(e ->
                    e.authenticationEntryPoint(new HttpStatusEntryPoint(HttpStatus.UNAUTHORIZED))
                )
                .csrf()
                .disable()
                .cors()
        ;

    }

    @Bean
    CorsConfigurationSource corsConfigurationSource(){
        CorsConfiguration config = new CorsConfiguration();
        config.setAllowedOrigins(Arrays.asList(
                "http://localhost:11013","http://localhost:8000"
        ));
        config.setAllowedMethods(Arrays.asList("GET","POST"));
        config.setAllowCredentials(true);
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", config);
        return source ;
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService)
                .passwordEncoder(passwordEncoder());
    }

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

}
