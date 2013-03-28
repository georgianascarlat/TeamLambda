package state;

import mediator.MediatorGUI;
import models.*;

import javax.swing.*;
import javax.swing.table.TableModel;
import java.awt.event.ActionListener;
import java.util.List;

import static models.MyTableModel.STATUS_COLUMN;
import static models.MyTableModel.USER_LIST_COLUMN;
import static models.StatusTypes.Offer_Made;
import static models.StatusTypes.Transfer_Started;

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

        table.getModel().setValueAt(StatusTypes.Active, row, MyTableModel.STATUS_COLUMN);

        inquire(row);
        table.repaint();


    }


    @Override
    public void dropOffer(int row) {

        table.getModel().setValueAt(StatusTypes.Inactive, row, MyTableModel.STATUS_COLUMN);
        DefaultListModel listModel = (DefaultListModel) table.getModel().getValueAt(row, MyTableModel.USER_LIST_COLUMN);
        listModel.removeAllElements();
        table.repaint();

    }

    protected boolean canAddUser(TableModel model, int row) {
        return !model.getValueAt(row, MyTableModel.STATUS_COLUMN).equals(StatusTypes.Inactive);
    }

    @Override
    public void login() {
        //does nothing
    }

    @Override
    public void auctionStatusChanged(String service, Auction auction) {

        int serviceRow = getServiceRow(service);
        DefaultListModel listModel ;

        if(serviceRow < 0){
            return;
        }

        listModel = (DefaultListModel) table.getModel().getValueAt(serviceRow,USER_LIST_COLUMN);
        StatusTypes status = auction.getStatus();
        switch (status){
            case Offer_Made:

                listModel.removeElement(auction);
                listModel.addElement(auction);

                break;
            case Inactive:

                listModel.removeElement(auction);

                break;
            case Offer_Accepted:

                listModel.removeAllElements();
                auction.setStatus(Transfer_Started);
                listModel.addElement(auction);

                break;
            default:
                throw new IllegalArgumentException("Cannot change auction to state "+auction.getStatus());
        }

        table.repaint();
    }



    @Override
    protected void verifyStatus(int row) {
        //does nothing
    }
}
