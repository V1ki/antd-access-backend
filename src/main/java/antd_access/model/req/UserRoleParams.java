package antd_access.model.req;

import antd_access.model.db.RoleEntity;
import antd_access.model.db.UserEntity;
import antd_access.model.db.UserRoleEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;
import java.util.stream.Collectors;

@Data
@ApiModel("用户角色关联参数")
public class UserRoleParams {

    @ApiModelProperty("角色id列表")
    private List<Long> roleIds ;


    public List<UserRoleEntity> toUserRoleEntityList(long uid){
        return roleIds.stream().map(roleId -> {
            UserRoleEntity userRoleEntity = new UserRoleEntity();
            userRoleEntity.setUser(new UserEntity(){{
                setUid(uid);
            }});
            userRoleEntity.setRole(new RoleEntity(){{
                setId(roleId);
            }});
            userRoleEntity.setCreatedAt(System.currentTimeMillis());
            return userRoleEntity;
        }).collect(Collectors.toList());
    }

}
