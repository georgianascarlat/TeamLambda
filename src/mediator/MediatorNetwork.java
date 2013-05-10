package mediator;

import app.Network;
import models.Auction;
import models.Service;

import java.util.List;

/**
 * The mediator interface visible from the Network.
 */
public interface MediatorNetwork {

    /**
     * Register network.
     *
     * @param network network
     */
    public void registerNetwork(Network network);

    /**
     * Inform about the login of a new user.
     *
     * @param username  user name
     * @param type     user type
     * @param services    list of the user's services
     */
    public void userLoggedIn(String username, String type, List<String> services);

    /**
     * Inform about the logiut of a new user.
     *
     * @param name user name
     * @param type  user type
     */
    public void userLoggedOut(String name, String type);

    /**
     * Informs about the change in status of an auction.
     *
     * @param service  target service name
     * @param auction  the new auction
     */
    public void auctionStatusChangeInform(String service, Auction auction);

    /**
     * Inform the buyer about the status of the download of a bought service.
     *
     * @param serviceName   target service name
     * @param auction  the auction in which the service was bought
     */
    public void startServiceTransferInform(String serviceName, Auction auction);
}
