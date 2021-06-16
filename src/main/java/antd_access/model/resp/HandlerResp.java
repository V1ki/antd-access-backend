package antd_access.model.resp;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@ApiModel("Response")
@AllArgsConstructor
public class HandlerResp {

    public static final int CODE_SUCCESS = 0 ;
    public static final int CODE_FAILED = 1 ;


    @ApiModelProperty(name="code" ,value= "业务返回码")
    private final int code ;
    @ApiModelProperty(name="msg" ,value= "描述信息")
    private final String msg ;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Object data  ;

    public HandlerResp(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public static HandlerResp success(String msg) {
        return new HandlerResp(CODE_SUCCESS, msg) ;
    }
    public static HandlerResp success(String msg,Object data) {
        return new HandlerResp(CODE_SUCCESS, msg, data) ;
    }

    public static HandlerResp failed(String msg) {
        return new HandlerResp(CODE_FAILED, msg) ;
    }


}
