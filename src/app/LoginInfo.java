package app;

/**
 * Created with IntelliJ IDEA.
 * User: nogai
 * Date: 3/24/13
 * Time: 5:14 PM
 * To change this template use File | Settings | File Templates.
 */
public class LoginInfo {

    private String username, type, password;

    LoginInfo(String username, String type, String password) {
        this.username = username;
        this.type = type;
        this.password = password;
    }

    String getUsername() {
        return username;
    }

    String getType() {
        return type;
    }

    String getPassword() {
        return password;
    }

    @Override
    public String toString() {
        return "LoginInfo{" +
                "username='" + username + '\'' +
                ", type='" + type + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}
