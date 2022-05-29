package antd_access.model.db;


import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Set;

@Getter
@Setter
@Entity(name = "Role")
@Table(name = "role")
public class RoleEntity {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private long id ;

    private String name ;

    private String identifier ;

    private String description ;

    private int priority ;

    private boolean disabled ;

    /**
     * 用户创建时间, timestamp .unix time
     */
    private long createdAt ;

    private long updatedAt ;


    @OneToMany(mappedBy = "role", fetch = FetchType.EAGER, orphanRemoval = true)
    private Set<RolePermissionEntity> permissions ;

    @OneToMany(mappedBy = "role", cascade = {},fetch = FetchType.EAGER,orphanRemoval = true)
    private Set<UserRoleEntity> users ;
}
