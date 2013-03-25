package state;

import mediator.MediatorGUI;
import models.MenuItemType;
import models.PopupListener;
import models.PopupType;
import models.TablePopupListener;

import javax.swing.*;
import java.awt.event.ActionListener;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: nogai
 * Date: 3/25/13
 * Time: 12:18 PM
 * To change this template use File | Settings | File Templates.
 */
public class SellerSessionState extends SessionState {


    public SellerSessionState(List<String> services, ActionListener actionListener, MediatorGUI mediatorGUI) {
        super(services, actionListener, mediatorGUI);
    }

    @Override
    protected PopupListener getTableMouseListener() {
        return new TablePopupListener(new JPopupMenu());
    }

    @Override
    protected PopupListener getListMouseListener() {

        return createMouseListener(PopupType.ListPopup,
                MenuItemType.MakeOffer, MenuItemType.DropAuction);

    }

    @Override
    public void launchOffer(int row) {
        //does nothing
        //does nothing
    }

    @Override
    public void dropOffer(int row) {
        //does nothing
    }


}
