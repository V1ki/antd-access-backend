package antd_access.controller;

import antd_access.model.db.User;
import antd_access.model.req.user.UserReq;
import antd_access.model.resp.HandlerResp;
import antd_access.repository.db.UserRepository;
import io.swagger.annotations.*;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
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


    final UserRepository userRepository;
    final PasswordEncoder passwordEncoder;


    @PostMapping("/register")
    @ApiOperation(value = "register", notes = "注册用户")
    @ApiResponses({
            @ApiResponse(code = 200, message = "请求成功,但是不一定注册成功!")
    })
    public HandlerResp registerUser(@Valid @RequestBody UserReq userReq,
            /* 忽略此参数 */ @ApiIgnore BindingResult result) {
        if (result.hasErrors()) {
            return HandlerResp.failed(result.getAllErrors().get(0).getDefaultMessage());
        }
        if (userRepository.existsByUsername(userReq.getUsername())) {
            return HandlerResp.failed("用户名已存在!");
        }
        User user = new User();
        user.setUsername(userReq.getUsername());

        // 这样对吗? 直接保存明文密码,这样好吗?
//        user.setPassword(  userReq.getPassword());
        user.setPassword(passwordEncoder.encode(userReq.getPassword()));

        user.setCreatedAt(System.currentTimeMillis());
        user.setUpdatedAt(System.currentTimeMillis());

        userRepository.save(user);

        return HandlerResp.success("注册成功");
    }


    @GetMapping("/current")
    @ApiOperation(value = "获取当前用户", notes = "获取当前y已经登录的用户")
    public HandlerResp fetchCurrentUser(@ApiIgnore @AuthenticationPrincipal User user) {
        return HandlerResp.success("获取当前用户成功", user);
    }

    @PostMapping("/add")
    @ApiOperation(value = "增加用户", notes = "增加用户,需要增加用户操作的权限")
    public HandlerResp addUser(@Valid @RequestBody UserReq userReq,
            /* 忽略此参数 */ @ApiIgnore BindingResult result) {

        if (result.hasErrors()) {
            return HandlerResp.failed(result.getAllErrors().get(0).getDefaultMessage());
        }
        if (userRepository.existsByUsername(userReq.getUsername())) {
            return HandlerResp.failed("用户名已存在!");
        }
        User user = new User();
        user.setUsername(userReq.getUsername());
        user.setPassword(passwordEncoder.encode(userReq.getPassword()));
        user.setCreatedAt(System.currentTimeMillis());
        user.setUpdatedAt(System.currentTimeMillis());

        userRepository.save(user);

        return HandlerResp.success("注册成功");
    }

    @PutMapping("/{username}")
    @ApiOperation(value = "修改用户",notes = "修改用户,需要增加修改用户的权限")
    public HandlerResp updateUser(
            @ApiParam("用户名") @PathVariable("username") String username,
            @Valid @RequestBody UserReq userReq,
            /* 忽略此参数 */ @ApiIgnore BindingResult result){

        if(result.hasErrors()) {
            return HandlerResp.failed(result.getAllErrors().get(0).getDefaultMessage());
        }
        if(!userRepository.existsByUsername(userReq.getUsername())){
            return HandlerResp.failed("用户名不存在!");
        }
        User user = userRepository.findByUsername(username).orElse(null) ;

        // 这样对吗? 直接保存明文密码,这样好吗?
    //        user.setPassword(  userReq.getPassword());
        user.setPassword( passwordEncoder.encode(userReq.getPassword()) );
        user.setUpdatedAt(System.currentTimeMillis());

        userRepository.save(user) ;

        return HandlerResp.success("注册成功");
    }


    @GetMapping("/list")
    @ApiOperation(value = "获取用户",notes = "获取用户,需要增加用户的基本权限")
    public HandlerResp fetchUserList(
    @ApiParam("页码") @RequestParam(value = "current",defaultValue = "1") int current,
    @ApiParam("页面容量") @RequestParam(value = "pageSize",defaultValue = "10") int pageSize
        ){
        Page<User> users = userRepository.findAll(PageRequest.of(current- 1,pageSize));
        return HandlerResp.success("获取用户成功",users.getContent());
    }


    @DeleteMapping("/{username}")
    @ApiOperation(value = "删除用户",notes = "删除用户,需要增加删除用户的权限")
    public HandlerResp deleteUser(
            @ApiParam("用户名") @PathVariable("username") String username
        ){
        userRepository.deleteByUsername(username);
        return HandlerResp.success("删除用户成功");
    }


}
