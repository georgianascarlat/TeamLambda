package app;

import models.Auction;
import models.Service;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: nogai
 * Date: 3/24/13
 * Time: 10:59 PM
 * To change this template use File | Settings | File Templates.
 */
public interface Network {

    public void offerLaunched(String username, String service);

    public void removeUserFromLists(String username, String type);

    public void offerDropped(String username, String service);

    public void newUser(String username, String type, List<String> services);

    public void auctionStatusChangeRequest(String sourceUser, String sourceUserType, String service, Auction auction);

    public void startServiceTransferRequest(String username, String service, Auction auction);

    public void auctionStatusChangeInform(String sourceUser, String sourceUserType, String service, Auction auction);

    public Service serviceStatusRequest(String username,String service, Auction auction);
}
