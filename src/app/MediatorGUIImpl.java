package app;

import app.MediatorGUI;

/**
 * Created with IntelliJ IDEA.
 * User: nogai
 * Date: 3/24/13
 * Time: 4:08 PM
 * To change this template use File | Settings | File Templates.
 */
public class MediatorGUIImpl implements MediatorGUI {

    protected GUI gui;


    @Override
    public void registerGUI(GUI gui) {
        this.gui = gui;
    }

    @Override
    public void logIn() {

        LoginInfo info = gui.getLoginInfo();


        System.out.println(info);
        if(info != null){
            gui.logIn(info);
        }

    }

    @Override
    public void logOut() {
        //To change body of implemented methods use File | Settings | File Templates.
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
