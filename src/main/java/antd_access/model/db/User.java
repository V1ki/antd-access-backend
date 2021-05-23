package antd_access.model.db;

import lombok.Data;
import org.hibernate.validator.constraints.UniqueElements;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.util.Collection;

@Data
@Entity
@Table(uniqueConstraints = {
        @UniqueConstraint(columnNames = "username")
})
public class User implements UserDetails {


    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private long uid ;


    private String username ;
    private String password ;

    private String avatar ;

    /**
     * 用户创建时间, timestamp .unix time
     */
    private long createdAt ;

    private long updatedAt ;


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
