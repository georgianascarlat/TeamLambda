package state;

import mediator.MediatorGUI;
import models.*;
import worker.FileTransferWorker;

import javax.swing.*;
import javax.swing.table.TableModel;
import java.awt.event.ActionListener;
import java.util.List;

import static models.MyTableModel.*;
import static models.StatusTypes.*;

/**
 * Created with IntelliJ IDEA.
 * User: nogai
 * Date: 3/25/13
 * Time: 12:18 PM
 * To change this template use File | Settings | File Templates.
 */
public class SellerSessionState extends SessionState {


    public SellerSessionState(String stateName, List<String> services, ActionListener actionListener, MediatorGUI mediatorGUI) {
        super(stateName, services, actionListener, mediatorGUI);


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

    }

    @Override
    public void dropOffer(int row) {
        //does nothing
    }

    @Override
    protected boolean canAddUser(TableModel model, int row) {
        return true;
    }


    @Override
    public void login() {

        int rowCount = table.getRowCount();

        for (int row = 0; row < rowCount; row++) {

            inquire(row);

            verifyStatus(row);
        }

        table.repaint();
    }

    @Override
    public void auctionStatusChanged(String service, Auction auction) {

        int serviceRow = getServiceRow(service);
        DefaultListModel listModel;

        if (serviceRow < 0) {
            return;
        }

        listModel = (DefaultListModel) table.getModel().getValueAt(serviceRow, USER_LIST_COLUMN);

        switch (auction.getStatus()) {

            case Offer_Made:

                if (listModel.removeElement(auction))
                    listModel.addElement(auction);

                break;

            case Inactive:

                listModel.removeElement(auction);

                resetTransfer(auction.getUser(),table.getModel(),serviceRow);

                verifyStatus(serviceRow);

                break;

            case Offer_Exceeded:


                if (listModel.removeElement(auction))
                    listModel.addElement(auction);


                break;

            case Offer_Refused:


                if (listModel.removeElement(auction))
                    listModel.addElement(auction);


                break;

            case Offer_Accepted:

                if (listModel.removeElement(auction)) {
                    listModel.addElement(auction);
                    new FileTransferWorker(mediator, service, auction, table, serviceRow).execute();
                }

                break;

            default:
                throw new IllegalArgumentException("Cannot change auction to state " + auction.getStatus());
        }

        table.repaint();

    }


    @Override
    protected void verifyStatus(int row) {

        DefaultListModel listModel = (DefaultListModel) table.getModel().getValueAt(row, USER_LIST_COLUMN);

        if (listModel.getSize() == 0) {
            table.getModel().setValueAt(Inactive, row, STATUS_COLUMN);
        } else if (Inactive.equals(table.getModel().getValueAt(row, STATUS_COLUMN))) {
            table.getModel().setValueAt(Active, row, STATUS_COLUMN);
        }


    }


}
