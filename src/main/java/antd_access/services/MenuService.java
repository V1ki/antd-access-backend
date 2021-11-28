package antd_access.services;

import antd_access.model.db.MenuEntity;
import antd_access.model.req.menu.MenuParams;
import antd_access.model.resp.MenuVO;
import antd_access.repository.db.MenuRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class MenuService {


    private final MenuRepository menuRepository;

    /**
     * 创建菜单、
     * @param menuParams 菜单参数
     */
    public void createMenu(MenuParams menuParams){
        menuRepository.save(menuParams.toEntity());
    }

    /**
     * 更新菜单
     * @param menuParams 菜单参数
     */
    public void updateMenu(MenuParams menuParams){
        menuRepository.save(menuParams.toEntity());
    }

    /**
     * 删除菜单
     * @param mid 菜单id
     */
    public void deleteMenu(long mid){
        menuRepository.deleteById(mid);
    }

    /**
     * 查询菜单列表
     * @param page int 页码 从1开始
     * @param size int 每页数量
     * @param sort List<String> 排序字段
     * @param order List<String> 排序方式 排序默认为 asc， 列表长度与排序字段长度一致
     * @return Page<MenuEntity> 菜单列表
     */
    public Page<MenuEntity> getMenuList(int page, int size, List<String> sort, List<String> order){

        if (order == null || sort == null ){
            log.error("排序字段或排序方式为空");
            return Page.empty() ;
        }
        if ( sort.size() != order.size() ){
            log.error("sort and order size not equal");
            return Page.empty() ;
        }
        List<Sort.Order> orderList = new ArrayList<>();
        for (int i = 0; i < sort.size(); i++) {
            orderList.add(new Sort.Order( Sort.Direction.fromString(order.get(i)), sort.get(i)));
        }

        return menuRepository.findAll(
                PageRequest.of(
                        page - 1, size, Sort.by(orderList)
                )
        );
    }


    /**
     * 查询菜单详情
     * @param mid 菜单id
     * @return MenuEntity 菜单详情
     */
    public Optional<MenuEntity> getMenuDetail(long mid){
        return menuRepository.findById(mid);
    }

    /**
     * 查询当前用户的菜单列表
     * @return List<MenuVO> 菜单列表
     */
    public List<MenuVO> getCurrentMenu() {
        return menuRepository.findAll().stream()
                .map(MenuVO::entity2VO)
                .collect(Collectors.toList());
    }
}
