package app;

import app.MediatorGUI;
import exceptions.NoSuchUserException;

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
public class MediatorGUIImpl implements MediatorGUI {

    protected GUI gui;
    protected WebServiceClient webServiceClient;


    @Override
    public void registerGUI(GUI gui) {
        this.gui = gui;
    }

    @Override
    public void registerWebServiceClient(WebServiceClient webServiceClient) {
        this.webServiceClient = webServiceClient;
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
    public void launchOffer() {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void revokeOffer() {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void makeOffer() {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void acceptOffer() {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void surpassOffer() {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void denyOffer() {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void doTransfer() {
        //To change body of implemented methods use File | Settings | File Templates.
    }
}
