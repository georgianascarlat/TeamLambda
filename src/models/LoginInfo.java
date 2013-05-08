package models;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;

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
    private List<String> serviceNames;

    public LoginInfo(String username, String fileName, String password) {
        this.username = username;
        this.fileName = fileName;
        this.password = password;
        serviceNames = new LinkedList<String>();
    }

    @Override
    public String toString() {
        return "LoginInfo{" +
                "username='" + username + '\'' +
                ", type='" + type + '\'' +
                ", password='" + password + '\'' +
                ", fileName='" + fileName + '\'' +
                ", serviceNames=" + serviceNames +
                '}';
    }

    public void readServicesFromWishList() throws FileNotFoundException {

        Scanner scanner = new Scanner(new File(fileName));

        this.type = scanner.nextLine().trim();

        while(scanner.hasNext()){
            serviceNames.add(scanner.nextLine().trim());
        }

    }

    public List<String> getServiceNames() {
        return serviceNames;
    }

    public void setServiceNames(List<String> serviceNames) {
        this.serviceNames = serviceNames;
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
