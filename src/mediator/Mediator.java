package mediator;

import app.*;
import exceptions.NoSuchUserException;
import models.Auction;
import models.LoginInfo;
import models.Service;

import javax.swing.*;
import java.awt.*;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: nogai
 * Date: 3/24/13
 * Time: 4:08 PM
 * To change this template use File | Settings | File Templates.
 */
public class Mediator implements MediatorGUI, MediatorNetwork, MediatorWebServiceClient {

    protected GUI gui;
    protected WebServiceClient webServiceClient;
    protected Network network;
    protected LoginInfo info;


    @Override
    public void registerGUI(GUI gui) {
        this.gui = gui;
    }

    @Override
    public void registerWebServiceClient(WebServiceClient webServiceClient) {
        this.webServiceClient = webServiceClient;
    }


    @Override
    public void registerNetwork(Network network) {
        this.network = network;
    }

    @Override
    public void logIn() {

        info = gui.getLoginInfo();

        if (info == null)
            return;

        List<String> services;
        try {

            services = webServiceClient.getServices(info);
            gui.logIn(info, services);

            network.newUser(info.getUsername(), info.getType(), services);

        } catch (NoSuchUserException e) {

            JOptionPane.showMessageDialog((Component) gui, "Invalid username or password.");
        }

    }

    @Override
    public void logOut() {

        gui.logOut();

        network.removeUserFromLists(info.getUsername(), info.getType());

    }

    @Override
    public void launchOffer(String selectedService, int selectedServiceRow) {

        gui.launchOffer(selectedServiceRow);
        network.offerLaunched(info.getUsername(), selectedService);

    }

    @Override
    public void dropOffer(String selectedService, int selectedServiceRow) {

        gui.dropOffer(selectedServiceRow);
        network.offerDropped(info.getUsername(), selectedService);

    }


    @Override
    public List<String> inquireService(String service) {

        return network.inquireService(service);
    }

    @Override
    public void auctionStatusChangeRequest(String service, Auction auction) {

        gui.auctionStatusChanged(service, auction);

        network.auctionStatusChangeRequest(info.getUsername(), info.getType(), service, auction);
    }

    @Override
    public void auctionStatusChangeInform(String service, Auction auction) {


        gui.auctionStatusChanged(service, auction);


    }

    @Override
    public Service serviceTransfer(String service, Auction auction) {

        return webServiceClient.serviceTransfer(info.getUsername(), service, auction);
    }


    @Override
    public void userLoggedIn(String username, String type, List<String> services) {

        gui.addUser(username, type, services);
    }

    @Override
    public void userLoggedOut(String name, String type) {

        gui.removeUser(name, type);
    }
}
