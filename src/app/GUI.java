package app;

import models.LoginInfo;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: nogai
 * Date: 3/24/13
 * Time: 2:41 PM
 * To change this template use File | Settings | File Templates.
 */
public interface GUI {


    public LoginInfo getLoginInfo();

    public void logIn(LoginInfo info, List<String> services);

    public void logOut();

    public void launchOffer(int row);

    public void dropOffer(int row);

    public void newUserAppeared(String username, String type, List<String> services);

    public void removeUser(String name, String type);
}
