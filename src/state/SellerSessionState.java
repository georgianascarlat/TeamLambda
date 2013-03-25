package state;

import app.MediatorGUI;
import commands.CommandMenuItem;
import models.MenuItemType;
import models.PopupListener;

import javax.swing.*;
import java.awt.event.ActionListener;
import java.awt.event.MouseListener;
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
    protected MouseListener getTableMouseListener() {
        return new PopupListener(new JPopupMenu());
    }

    @Override
    protected MouseListener getListMouseListener() {

        return createMouseListener(MenuItemType.MakeOffer,MenuItemType.DropAuction);

    }

    @Override
    public void launchOffer() {
        //To change body of implemented methods use File | Settings | File Templates.
    }


}
