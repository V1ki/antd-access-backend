package antd_access.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MenuController {

    @GetMapping("/home")
    public String home(){
        return "Hello SpringBoot" ;
    }

}
