package antd_access.controller;

import antd_access.model.db.User;
import antd_access.model.req.user.UserReq;
import antd_access.model.resp.HandlerResp;
import antd_access.repository.db.UserRepository;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/user")
public class UserController {


    final UserRepository userRepository ;
    final PasswordEncoder passwordEncoder ;


    @PostMapping("/register")
    public HandlerResp registerUser(@RequestBody UserReq userReq){

        User user = new User();
        user.setUsername(userReq.getUsername());

        // 这样对吗? 直接保存明文密码,这样好吗?
//        user.setPassword(  userReq.getPassword());
        user.setPassword( passwordEncoder.encode(userReq.getPassword()) );

        user.setCreatedAt(System.currentTimeMillis());
        user.setUpdatedAt(System.currentTimeMillis());

        userRepository.save(user) ;

        return HandlerResp.success("注册成功");
    }

}
