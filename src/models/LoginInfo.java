package models;

/**
 * Created with IntelliJ IDEA.
 * User: nogai
 * Date: 3/24/13
 * Time: 5:14 PM
 * To change this template use File | Settings | File Templates.
 */
public class LoginInfo {

    private String username, type, password;
    private String fileName;

    public LoginInfo(String username, String type, String password) {
        this.username = username;
        this.type = type;
        this.password = password;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getUsername() {
        return username;
    }

    public String getType() {
        return type;
    }

    public String getPassword() {
        return password;
    }

}
