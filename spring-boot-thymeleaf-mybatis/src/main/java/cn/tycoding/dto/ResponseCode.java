package cn.tycoding.dto;

import cn.tycoding.enums.StatusEnums;
import lombok.Data;

/**
 * @author tycoding
 * @date 2019-02-24
 */
@Data
public class ResponseCode {

    private Integer code;
    private String msg;
    private Object data;

    public ResponseCode() {
    }

    public ResponseCode(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public ResponseCode(StatusEnums enums) {
        this.code = enums.getCode();
        this.msg = enums.getInfo();
    }

    public ResponseCode(StatusEnums enums, Object data) {
        this.code = enums.getCode();
        this.msg =enums.getInfo();
        this.data = data;
    }

    public static ResponseCode success() {
        return new ResponseCode(StatusEnums.SUCCESS);
    }

    public static ResponseCode success(Object data) {
        return new ResponseCode(StatusEnums.SUCCESS, data);
    }

    public static ResponseCode error() {
        return new ResponseCode(StatusEnums.SYSTEM_ERROR);
    }
}