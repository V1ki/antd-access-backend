package antd_access.controller;

import antd_access.model.db.MenuEntity;
import antd_access.model.db.UserEntity;
import antd_access.model.req.menu.MenuParams;
import antd_access.model.resp.HandlerResp;
import antd_access.model.resp.MenuVO;
import antd_access.services.MenuService;
import io.swagger.annotations.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import java.util.List;

@Slf4j
@Api(tags = "Menu")
@RestController
@RequiredArgsConstructor
@RequestMapping("/menu")
public class MenuController {

    private final MenuService menuService ;

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



    @ApiOperation(value ="创建menu",notes = "创建menu")
    @PostMapping("/create")
    public HandlerResp<Object> create(
            @RequestBody MenuParams menuParams,
            @ApiIgnore @AuthenticationPrincipal UserEntity userEntity
    ){
        log.info("create menu with:{}", userEntity.getUsername());
        menuService.createMenu(menuParams);
        return HandlerResp.success("");
    }


    @ApiOperation(value ="更新menu",notes = "更新menu")
    @PostMapping("/{mid}")
    public HandlerResp<MenuVO> create(
            @PathVariable("mid") Long mid,
            @RequestBody MenuParams menuParams,
            @ApiIgnore @AuthenticationPrincipal UserEntity userEntity
    ){
        log.info("update menu with:{}", userEntity.getUsername());
        menuService.updateMenu(menuParams);
        return HandlerResp.success("");
    }



}
