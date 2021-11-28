package antd_access.model.req.menu;

import antd_access.model.db.MenuEntity;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class MenuParams {
    private Long mid ;

    private String identifier;

    private String name ;

    private String icon ;

    private String path ;

    private Long parentId ;

    private boolean hide ;

    private int idx ;


    public MenuEntity toEntity() {
        MenuEntity entity = new MenuEntity();
        if(mid != null) {
            entity.setMid(mid);
            entity.setUpdatedAt(System.currentTimeMillis());
        }
        else {
            entity.setCreatedAt(System.currentTimeMillis());
        }
        entity.setIdentifier(identifier);
        entity.setName(name);
        entity.setIcon(icon);
        entity.setPath(path);
        entity.setParentId(parentId);
        entity.setHide(hide);
        entity.setIdx(idx);
        return entity;
    }

}
