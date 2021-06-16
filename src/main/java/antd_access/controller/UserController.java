package antd_access.controller;

import antd_access.model.db.User;
import antd_access.model.req.user.UserReq;
import antd_access.model.resp.HandlerResp;
import antd_access.repository.db.UserRepository;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import javax.validation.Valid;

@RequiredArgsConstructor
@RestController
@RequestMapping("/user")
@Api(tags = "User")
@CrossOrigin
public class UserController {


    final UserRepository userRepository ;
    final PasswordEncoder passwordEncoder ;


    @PostMapping("/register")
    @ApiOperation(value = "register",notes = "注册用户")
    @ApiResponses({
            @ApiResponse(code = 200,message = "请求成功,但是不一定注册成功!")
    })
    public HandlerResp registerUser(@Valid @RequestBody UserReq userReq,
                                   /* 忽略此参数 */ @ApiIgnore BindingResult result){
        if(result.hasErrors()) {
            return HandlerResp.failed(result.getAllErrors().get(0).getDefaultMessage());
        }
        if(userRepository.existsByUsername(userReq.getUsername())){
            return HandlerResp.failed("用户名已存在!");
        }
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


    @GetMapping("/current")
    @ApiOperation(value = "获取当前用户",notes = "获取当前y已经登录的用户")
    public HandlerResp fetchCurrentUser(@ApiIgnore @AuthenticationPrincipal User user) {
        return HandlerResp.success("获取当前用户成功",user) ;
    }


}
