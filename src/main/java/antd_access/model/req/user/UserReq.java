package antd_access.model.req.user;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ApiModel("User")
public class UserReq {

    @Size(min = 3, max = 100, message = "用户名长度不满足")
    @Pattern(regexp = "^[0-9a-zA-Z.]*$", message = "用户名格式不正确")
    @ApiModelProperty(value = "username", notes = "用户名", example = "admin")
    private String username ;


    @Size(min = 8, max = 100, message = "密码长度不满足")
    @Pattern(regexp = "^(?=.*[0-9]).*$",message = "密码必须包含一位数字")
    @Pattern(regexp = "^(?=.*[A-Z]).*$",message = "密码必须包含大写字母")
    @Pattern(regexp = "^(?=.*[a-z]).*$",message = "密码必须包含小写字母")
    @Pattern(regexp = "^(?=\\S+$).*$",message = "密码不能包含空格")
    @ApiModelProperty(value = "password", notes = "密码", example = "Abcd1234")
    private String password ;

}
