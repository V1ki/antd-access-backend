package antd_access.controller;

import io.swagger.annotations.*;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Api(tags = "Menu")
@RestController
public class MenuController {

    @GetMapping("/home")
    @ApiOperation(value = "home",notes = "测试请求")
    @ApiResponses({
            @ApiResponse(code = 200,message = "请求成功!"),
            @ApiResponse(code = 401, message = "未登录!")
    })
    public String home(
            @ApiParam(name ="index",value = "索引,主要用于balalalalala",defaultValue = "0")
            @RequestParam(value = "index",defaultValue = "0") int index
    ){
        return "Hello SpringBoot" ;
    }

}
