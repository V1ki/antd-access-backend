package antd_access.model.resp;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@ApiModel("Response")
@AllArgsConstructor
public class HandlerResp<T> {

    public static final int CODE_SUCCESS = 0 ;
    public static final int CODE_FAILED = 1 ;


    @ApiModelProperty(name="code" ,value= "业务返回码")
    private final int code ;
    @ApiModelProperty(name="msg" ,value= "描述信息")
    private final String msg ;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private T data  ;

    @JsonInclude(JsonInclude.Include.NON_DEFAULT)
    private int total ;

    public HandlerResp(){
        this.code = CODE_FAILED ;
        this.msg = "" ;
    }

    public HandlerResp(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public static <T> HandlerResp<T> success(String msg) {
        return new HandlerResp<>(CODE_SUCCESS, msg) ;
    }
    public static <T>  HandlerResp<T> success(String msg,T data) {
        return new HandlerResp<>(CODE_SUCCESS, msg, data,0) ;
    }
    public static <T>  HandlerResp<T> success(String msg,T data, int total) {
        return new HandlerResp<>(CODE_SUCCESS, msg, data,total) ;
    }

    public static <T>  HandlerResp<T> failed(String msg) {
        return new HandlerResp<>(CODE_FAILED, msg) ;
    }


}
