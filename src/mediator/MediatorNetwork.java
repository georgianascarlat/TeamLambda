package mediator;

import app.Network;
import models.Auction;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: nogai
 * Date: 3/26/13
 * Time: 12:43 AM
 * To change this template use File | Settings | File Templates.
 */
public interface MediatorNetwork {

    public void registerNetwork(Network network);

    public void userLoggedIn(String username, String type, List<String> services);

    public void userLoggedOut(String name, String type);

    public void auctionStatusChangeInform(String service, Auction auction);
}
