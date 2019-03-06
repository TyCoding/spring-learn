package cn.tycoding.utils;

/**
 * @author tycoding
 * @date 2019-03-01
 */
public class ResponseCode {

    private Object data;

    private Long total;

    private String msg;

    public ResponseCode() {
    }

    public ResponseCode(String msg) {
        this.msg = msg;
    }

    public ResponseCode(Object data, Long total) {
        this.data = data;
        this.total = total;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public Long getTotal() {
        return total;
    }

    public void setTotal(Long total) {
        this.total = total;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
