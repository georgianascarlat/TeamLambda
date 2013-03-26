package state;

import mediator.MediatorGUI;
import models.*;

import javax.swing.table.TableModel;
import java.awt.event.ActionListener;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: nogai
 * Date: 3/25/13
 * Time: 12:18 PM
 * To change this template use File | Settings | File Templates.
 */
public class BuyerSessionState extends SessionState {


    public BuyerSessionState(String stateName, List<String> services, ActionListener actionListener, MediatorGUI mediatorGUI) {
        super(stateName, services, actionListener, mediatorGUI);


    }

    @Override
    protected PopupListener getTableMouseListener() {

        return createMouseListener(PopupType.TablePopup,
                MenuItemType.LaunchOfferRequest, MenuItemType.DropOfferRequest);
    }


    @Override
    protected PopupListener getListMouseListener() {


        return createMouseListener(PopupType.ListPopup,
                MenuItemType.AcceptOffer, MenuItemType.RefuseOffer);

    }

    @Override
    public void launchOffer(int row) {

        table.getModel().setValueAt(StatusTypes.No_Offer, row, MyTableModel.STATUS_COLUMN);
        table.repaint();


    }

    @Override
    public void dropOffer(int row) {

        table.getModel().setValueAt(StatusTypes.Inactive, row, MyTableModel.STATUS_COLUMN);
        table.repaint();

    }

    protected boolean canAddUser(TableModel model, int row) {
        return !model.getValueAt(row, MyTableModel.STATUS_COLUMN).equals(StatusTypes.Inactive);
    }
}
