package antd_access.model.req.menu;

import antd_access.model.db.RoleEntity;
import lombok.Data;

@Data
public class RoleParams {

    private String name ;

    private String identifier ;

    private String description ;

    private int priority ;

    private boolean disabled ;



    public RoleEntity toEntity(){
        RoleEntity roleEntity = new RoleEntity();
        roleEntity.setName(name);
        roleEntity.setIdentifier(identifier);
        roleEntity.setDescription(description);
        roleEntity.setPriority(priority);
        roleEntity.setDisabled(disabled);

        roleEntity.setCreatedAt(System.currentTimeMillis());
        roleEntity.setUpdatedAt(System.currentTimeMillis());
        return roleEntity;
    }


}
