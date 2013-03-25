package app;

import exceptions.NoSuchUserException;

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

    public void registerWebServiceClient(WebServiceClient webServiceClient);

    //un utilizator face log in / log out
    public void logIn();

    public void logOut();

    // se lanseaza o cerere de oferta
    public void launchOffer();

    // se anuleaza o cerere de oferta
    public void revokeOffer();

    //  se face o oferta
    public void makeOffer();

    //oferta este acceptata/depasita/refuzata
    public void acceptOffer();

    public void surpassOffer();

    public void refuseOffer();

    // transferul de servicii/produse
    public void doTransfer();

}
