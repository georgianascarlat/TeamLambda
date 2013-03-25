package state;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: nogai
 * Date: 3/25/13
 * Time: 12:18 PM
 * To change this template use File | Settings | File Templates.
 */
public class BuyerSessionState extends SessionState {
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
    public void refuseOffer() {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void doTransfer() {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    protected BuyerSessionState(List<String> services) {
        super(services);
    }
}
