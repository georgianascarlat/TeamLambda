package mediator;

import app.GUI;
import app.Network;
import app.WebServiceClient;
import exceptions.NoSuchUserException;
import models.Auction;
import models.Service;

import java.util.List;

/**
 * The mediator interface visible from the GUI.
 */
public interface MediatorGUI {

    /**
     * Register GUI.
     *
     * @param gui  gui
     */
    public void registerGUI(GUI gui);


    /**
     * The user logs in.
     *
     */
    public void logIn();

    /**
     * The user logs out.
     */
    public void logOut();

    /**
     * An offer is launched.
     *
     * @param selectedService   the service for which the offer is launched
     * @param selectedServiceRow  the row number of the service
     */
    public void launchOffer(String selectedService, int selectedServiceRow);

    /**
     * An offer is dropped.
     *
     * @param selectedService   the service for which the offer is launched
     * @param selectedServiceRow  the row number of the service
     */
    public void dropOffer(String selectedService, int selectedServiceRow);


    /**
     * Retrieve the users that can offer/receive a certain service.
     *
     * @param service  service name
     * @return    list uf user names that can offer/receive the service
     */
    public List<String> inquireService(String service);

    /**
     * The status of an auction is changed and the network will be announced.
     *
     * @param service  target service name
     * @param auction  the new auction
     */
    public void auctionStatusChangeRequest(String service, Auction auction);


    /**
     * The seller requests the start of the service transfer.
     *
     * @param service  service name
     * @param auction  auction in which the service was selled
     *
     */
    public void startServiceTransferRequest(String service, Auction auction);

    /**
     * Request information about the state of the transfer of a service.
     *
     * @param service   service name
     * @param auction   auction
     * @return  a Service object that contains the state of the transfer
     */
    public Service serviceStatusRequest(String service, Auction auction);


}
