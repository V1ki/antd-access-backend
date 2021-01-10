package antd_access.model.db;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Data
@Entity
public class User {


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


}
