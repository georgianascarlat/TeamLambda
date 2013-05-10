package app;

import models.Auction;
import models.LoginInfo;
import models.Service;

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

    public void logIn(LoginInfo info);

    public void logOut();

    public void launchOffer(int row);

    public void dropOffer(int row);

    public void addUser(String username, String type, List<String> services);

    public void removeUser(String name, String type);

    public void auctionStatusChanged(String service, Auction auction);

    public void serviceTransfer(String username, String serviceName, Auction auction);
}
