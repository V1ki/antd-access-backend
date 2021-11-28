package antd_access.model.resp;

import antd_access.model.db.MenuEntity;
import lombok.Data;


@Data
public class MenuVO {
    private long mid ;

    private String identifier;

    private String name ;

    private String icon ;

    private String path ;

    private Long parentId ;

    private boolean hide ;

    private int idx ;

    public static MenuVO entity2VO(MenuEntity entity) {
        MenuVO vo = new MenuVO();
        vo.setMid(entity.getMid());
        vo.setIdentifier(entity.getIdentifier());
        vo.setName(entity.getName());
        vo.setIcon(entity.getIcon());
        vo.setPath(entity.getPath());
        vo.setParentId(entity.getParentId());
        vo.setHide(entity.isHide());
        vo.setIdx(entity.getIdx());
        return vo;
    }

}
