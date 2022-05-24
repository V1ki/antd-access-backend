package antd_access.model.req.user;

import antd_access.model.db.RoleEntity;
import antd_access.model.db.UserEntity;
import lombok.Data;

import java.util.List;

@Data
public class UserVO {

    private long uid ;


    private String username ;
    private String avatar ;

    private long createdAt ;

    private long updatedAt ;

    private List<RoleEntity> roleList ;


    public static UserVO entityToVO(UserEntity userEntity){
        UserVO userVO = new UserVO();
        userVO.setUid(userEntity.getUid());
        userVO.setUsername(userEntity.getUsername());
        userVO.setAvatar(userEntity.getAvatar());
        userVO.setCreatedAt(userEntity.getCreatedAt());
        userVO.setUpdatedAt(userEntity.getUpdatedAt());
        return userVO;
    }

}
