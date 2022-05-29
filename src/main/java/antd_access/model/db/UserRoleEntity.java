package antd_access.model.db;

import lombok.*;

import javax.persistence.*;
import java.io.Serializable;

@Data
@Entity(name = "UserRole")
@Table(name = "user_role")
public class UserRoleEntity {

    @Embeddable
    @Getter
    @Setter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class UserRoleKey implements Serializable {

        @Column(name = "user_id")
        private long userId ;

        @Column(name = "role_id")
        private long roleId ;
    }

    @EmbeddedId
    private UserRoleKey id;

    @ManyToOne(optional = false)
    @MapsId("userId")
    @JoinColumn(name = "user_id")
    private UserEntity user;

    @ManyToOne(optional = false)
    @MapsId("roleId")
    @JoinColumn(name = "role_id")
    private RoleEntity role;

    private long createdAt ;
}
