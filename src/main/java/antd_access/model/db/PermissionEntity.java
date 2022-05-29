package antd_access.model.db;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.LinkedHashSet;
import java.util.Set;

@Setter
@Getter
@Entity(name = "Permission")
@Table(name = "permission")
public class PermissionEntity {


    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private long id ;


    @Column(name = "name")
    private String name;

    @Column(name = "description")
    private String description;

    @OneToMany(mappedBy = "permission", fetch = FetchType.EAGER, orphanRemoval = true)
    private Set<RolePermissionEntity> roles ;

}
