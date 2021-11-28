package antd_access.model.db;

import lombok.*;
import org.hibernate.Hibernate;

import javax.persistence.*;
import java.util.Objects;

@Getter
@Setter
@ToString
@RequiredArgsConstructor
@Entity
@Table(name = "menu")
public class MenuEntity {
    /**
     * {
     *     id: '1',
     *     name: 'welcome',
     *     icon: 'ad',
     *     path: '/welcome',
     *     parent: '0',
     *     hide: false,
     *     order: 0,
     *   }
     */

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private long mid ;

    @Column(unique = true, nullable = false)
    private String identifier;

    private String name ;

    private String icon ;

    private String path ;

    private Long parentId ;

    private boolean hide ;

    private int idx ;

    private long createdAt ;
    private long updatedAt ;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        MenuEntity entity = (MenuEntity) o;
        return Objects.equals(mid, entity.mid);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
