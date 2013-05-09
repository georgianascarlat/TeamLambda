package mediator;

import app.GUI;
import app.Network;
import app.WebServiceClient;
import exceptions.NoSuchUserException;
import models.Auction;
import models.Service;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: nogai
 * Date: 3/24/13
 * Time: 12:57 PM
 * To change this template use File | Settings | File Templates.
 * <p/>
 * un utilizator face log in / log out,
 * se lanseaza o cerere de oferta,
 * se anuleaza o cerere de oferta,
 * se face o oferta,
 * oferta este acceptata/depasita/refuzata,
 * transferul de servicii/produse.
 */
public interface MediatorGUI {

    public void registerGUI(GUI gui);


    //un utilizator face log in / log out
    public void logIn();

    public void logOut();

    // se lanseaza o cerere de oferta
    public void launchOffer(String selectedService, int selectedServiceRow);

    // se anuleaza o cerere de oferta
    public void dropOffer(String selectedService, int selectedServiceRow);


    public List<String> inquireService(String service);

    public void auctionStatusChangeRequest(String service, Auction auction);


    public Service serviceTransferFromSeller(String service, Auction auction);
}
