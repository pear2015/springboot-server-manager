package pear.mybatis.springboot.model;

/**
 * Created by PEAR on 2019/2/16.
 */
public class PasswordModel {
    private  String userId;
    private  String password;

    public String getPasswordnew() {
        return passwordnew;
    }

    public void setPasswordnew(String passwordnew) {
        this.passwordnew = passwordnew;
    }

    private  String passwordnew;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

}
