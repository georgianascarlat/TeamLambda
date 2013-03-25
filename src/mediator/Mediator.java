package mediator;

import app.*;
import exceptions.NoSuchUserException;
import models.LoginInfo;

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

        LoginInfo info = gui.getLoginInfo();

        if (info == null)
            return;

        List<String> services = null;
        try {

            services = webServiceClient.getServices(info);
            gui.logIn(info, services);

        } catch (NoSuchUserException e) {

            JOptionPane.showMessageDialog((Component) gui, "Invalid username or password.");
        }

    }

    @Override
    public void logOut() {

        gui.logOut();
    }

    @Override
    public void launchOffer(String selectedService, int selectedServiceRow) {

        gui.launchOffer(selectedServiceRow);

    }

    @Override
    public void dropOffer() {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void makeOffer() {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void acceptOffer() {
        System.out.println("Offer accepted");
    }

    @Override
    public void surpassOffer() {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void refuseOffer() {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void doTransfer() {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void dropAuction() {
        //To change body of implemented methods use File | Settings | File Templates.
    }
}
