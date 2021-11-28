package antd_access.controller;

import antd_access.model.db.MenuEntity;
import antd_access.model.resp.HandlerResp;
import antd_access.model.resp.MenuVO;
import antd_access.services.MenuService;
import io.swagger.annotations.*;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Api(tags = "Menu")
@RestController
@RequiredArgsConstructor
@RequestMapping("/menu")
public class MenuController {

    private final MenuService menuService ;

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

    @GetMapping("/list")
    @ApiOperation(value = "fetchMenuList",notes = "获取菜单列表")
    public HandlerResp<List<MenuVO>> fetchMenuList(
            @ApiParam(name= "page" ,value = "页码",defaultValue = "1") @RequestParam(value = "page", defaultValue = "1", required = false)  int page,
            @ApiParam(name= "size" , value = "页大小",defaultValue = "10") @RequestParam(value = "page", defaultValue = "10", required = false)  int size,
            @ApiParam(name= "sort" , value = "排序字段",defaultValue = "id") @RequestParam(value = "sort", defaultValue = "mid", required = false) List<String> sort,
            @ApiParam(name= "order" , value = "排序方式",defaultValue = "desc") @RequestParam(value = "order", defaultValue = "desc", required = false)  List<String> order
    ){
        Page<MenuEntity> entityPage = menuService.getMenuList(page,size,sort,order);
        Page<MenuVO> voPage = entityPage.map(MenuVO::entity2VO);

        return HandlerResp.success("",voPage.getContent(),(int)voPage.getTotalElements());
    }


    @GetMapping("/current")
    @ApiOperation(value = "fetchCurrentMenu",notes = "获取当前菜单列表")
    public HandlerResp<List<MenuVO>> fetchCurrentMenu(){
        List<MenuVO> voList = menuService.getCurrentMenu();
        return HandlerResp.success("",voList,voList.size());
    }

}
