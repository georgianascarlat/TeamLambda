package state;

import mediator.MediatorGUI;
import models.*;
import worker.FileTransferWorker;

import javax.swing.*;
import javax.swing.table.TableModel;
import java.awt.event.ActionListener;
import java.util.List;

import static models.MyTableModel.SERVICE_NAME_COLUMN;
import static models.MyTableModel.USER_LIST_COLUMN;

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

        DefaultListModel listModel = (DefaultListModel) table.getModel().getValueAt(row, MyTableModel.USER_LIST_COLUMN);
        String service = (String) table.getModel().getValueAt(row,SERVICE_NAME_COLUMN);

        if(alreadyHasBeenAccepted(service,new Auction(""),listModel)) {
            JOptionPane.showMessageDialog(null, "Can't drop accepted offer", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }


        table.getModel().setValueAt(StatusTypes.Inactive, row, MyTableModel.STATUS_COLUMN);

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
    public synchronized void auctionStatusChanged(String service, Auction auction) {

        int serviceRow = getServiceRow(service);
        DefaultListModel<Auction> listModel;

        if (serviceRow < 0) {
            return;
        }

        listModel = (DefaultListModel) table.getModel().getValueAt(serviceRow, USER_LIST_COLUMN);
        StatusTypes status = auction.getStatus();
        switch (status) {
            case Offer_Made:

                if (listModel.removeElement(auction))
                    listModel.addElement(auction);

                break;
            case Inactive:

                listModel.removeElement(auction);

                resetTransfer(auction.getUser(), table.getModel(), serviceRow);

                break;
            case Offer_Accepted:

                if (alreadyHasBeenAccepted(service, auction, listModel)) {
                    JOptionPane.showMessageDialog(null, "Can't accept another offer", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                if (listModel.removeElement(auction)) {
                    listModel.clear();
                    listModel.addElement(auction);
                    new FileTransferWorker(mediator, service, auction, table, serviceRow).execute();
                }
                break;
            case Transfer_Failed:
                if (listModel.removeElement(auction)) {
                    resetTransfer(auction.getUser(), table.getModel(), serviceRow);
                    listModel.addElement(auction);
                }
                break;
            default:
                throw new IllegalArgumentException("Cannot change auction to state " + auction.getStatus());
        }

        table.repaint();
    }


    @Override
    protected void verifyStatus(int row) {

    }
}
