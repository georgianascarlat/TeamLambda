package state;

import models.PopupListener;

import javax.swing.table.TableModel;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: nogai
 * Date: 3/26/13
 * Time: 2:17 AM
 * To change this template use File | Settings | File Templates.
 */
public class IdleSessionState extends SessionState {


    @Override
    protected PopupListener getTableMouseListener() {
        return null;
    }

    @Override
    protected PopupListener getListMouseListener() {
        return null;
    }

    @Override
    public void launchOffer(int row) {

    }

    @Override
    public void dropOffer(int row) {

    }

    @Override
    public void newUserAppeared(String username, String type, List<String> services) {

    }

    @Override
    protected boolean canAddUser(TableModel model, int row) {
        return false;  //To change body of implemented methods use File | Settings | File Templates.
    }
}
