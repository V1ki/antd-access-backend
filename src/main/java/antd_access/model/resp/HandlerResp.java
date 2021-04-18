package antd_access.model.resp;

import lombok.Data;

@Data
public class HandlerResp {

    public static final int CODE_SUCCESS = 0 ;
    public static final int CODE_FAILED = 1 ;



    private final int code ;
    private final String msg ;

    public static HandlerResp success(String msg) {
        return new HandlerResp(CODE_SUCCESS, msg) ;
    }

    public static HandlerResp failed(String msg) {
        return new HandlerResp(CODE_FAILED, msg) ;
    }


}
