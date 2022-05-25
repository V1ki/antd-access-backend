package antd_access.controller;

import antd_access.model.resp.HandlerResp;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Slf4j
@ControllerAdvice
public class CustomExceptionAdvice {

    @ResponseBody
    @ExceptionHandler(value = {MethodArgumentNotValidException.class})
    public HandlerResp<String > handleMethodArgNotValid(MethodArgumentNotValidException e
            , HttpServletRequest request, HttpServletResponse response
    ) {
        response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        return HandlerResp.failed(e.getAllErrors().get(0).getDefaultMessage());
    }

}
