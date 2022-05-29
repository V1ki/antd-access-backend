package antd_access.model.db;

import lombok.*;

import javax.persistence.*;

@Getter
@Setter
@Entity(name = "RolePermission")
@Table(name = "role_permission")
public class RolePermissionEntity {


    @Getter
    @Setter
    @Builder
    @Embeddable
    @NoArgsConstructor
    @AllArgsConstructor
    public static class RolePermissionId implements java.io.Serializable {

        @Column(name = "role_id")
        private long roleId;

        @Column(name = "permission_id")
        private long permissionId;
    }

    @EmbeddedId
    private RolePermissionId id ;

    @ManyToOne(optional = false)
    @MapsId("permissionId")
    @JoinColumn(name = "permission_id")
    private PermissionEntity permission;

    @ManyToOne(optional = false)
    @MapsId("roleId")
    @JoinColumn(name = "role_id")
    private RoleEntity role;


    @Column(name = "created_at")
    private long createdAt ;


}
