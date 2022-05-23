package antd_access.controller;


import antd_access.model.db.RoleEntity;
import antd_access.model.db.UserEntity;
import antd_access.model.req.menu.RoleParams;
import antd_access.model.resp.HandlerResp;
import antd_access.repository.db.RoleRepository;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/role")
public class RoleController {

    private final RoleRepository roleRepository ;


    @ApiOperation(value = "获取角色列表")
    @GetMapping("/list")
    public HandlerResp<List<RoleEntity>> fetchRoleList(
            @ApiParam(name= "page" ,value = "页码",defaultValue = "1") @RequestParam(value = "page", defaultValue = "1", required = false)  int page,
            @ApiParam(name= "size" , value = "页大小",defaultValue = "10") @RequestParam(value = "page", defaultValue = "10", required = false)  int size,
            @ApiParam(name= "sort" , value = "排序字段",defaultValue = "id") @RequestParam(value = "sort", defaultValue = "id", required = false) List<String> sort,
            @ApiParam(name= "order" , value = "排序方式",defaultValue = "desc") @RequestParam(value = "order", defaultValue = "desc", required = false)  List<String> order,
            @ApiIgnore @AuthenticationPrincipal UserEntity userEntity
    ) {
        List<Sort.Order> orderList = new ArrayList<>();
        for (int i = 0; i < sort.size(); i++) {
            orderList.add(new Sort.Order( Sort.Direction.fromString(order.get(i)), sort.get(i)));
        }
        Page<RoleEntity>  rolePages = roleRepository.findAll(PageRequest.of(page- 1 , size, Sort.by(orderList)));
        return HandlerResp.success("",rolePages.getContent(),(int)rolePages.getTotalElements());
    }


    @ApiOperation(value = "创建角色")
    @PostMapping("/create")
    public HandlerResp<String> createRole(
            @RequestBody RoleParams body,
            @ApiIgnore @AuthenticationPrincipal UserEntity userEntity
    ){
        log.info("{} create role: {} ",userEntity.getUsername(),body);
        roleRepository.save(body.toEntity());
        return HandlerResp.success("");
    }


    @ApiOperation(value = "修改角色")
    @PostMapping("/{roleId}")
    public HandlerResp<String> updateRole(
            @PathVariable("roleId") long roleId ,
            @RequestBody RoleParams body,
            @ApiIgnore @AuthenticationPrincipal UserEntity userEntity
    ){
        log.info("{} update  role: [{}]  {} ",userEntity.getUsername(), roleId,body);
        roleRepository.save(body.toEntity());
        return HandlerResp.success("");
    }





}
