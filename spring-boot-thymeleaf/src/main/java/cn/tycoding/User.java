package cn.tycoding;

import java.io.Serializable;

/**
 * @author tycoding
 * @date 2019-02-22
 */
public class User implements Serializable {

    private String username;
    private String password;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
