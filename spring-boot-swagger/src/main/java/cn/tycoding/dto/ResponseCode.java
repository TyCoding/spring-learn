package cn.tycoding.dto;

/**
 * 封装一个通用的Response结果信息类
 *
 * @author tycoding
 * @date 2019-02-27
 */
public class ResponseCode {

    private Long code;
    private String msg;
    private Object data;

    public ResponseCode() {
    }

    public ResponseCode(Long code, String msg, Object data) {
        this.code = code;
        this.msg = msg;
        this.data = data;
    }

    public static ResponseCode ok(String msg) {
        return new ResponseCode(200L, msg, null);
    }

    public static ResponseCode ok(String msg, Object data) {
        return new ResponseCode(200L, msg, data);
    }

    public Long getCode() {
        return code;
    }

    public void setCode(Long code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "ResponseCode{" +
                "code=" + code +
                ", msg='" + msg + '\'' +
                ", data=" + data +
                '}';
    }
}
