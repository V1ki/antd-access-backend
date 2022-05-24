package antd_access.model.db;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity(name = "UserRole")
@Table(name = "user_role")
public class UserRoleEntity {


    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private long id ;

    private long userId ;
    private long roleId ;

    private long createdAt ;

}
