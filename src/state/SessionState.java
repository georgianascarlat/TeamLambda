package state;

import mediator.MediatorGUI;
import commands.CommandMenuItem;
import models.*;

import javax.swing.*;
import javax.swing.table.TableModel;
import java.awt.event.ActionListener;
import java.util.List;

import static models.MyTableModel.*;

/**
 * Created with IntelliJ IDEA.
 * User: nogai
 * Date: 3/25/13
 * Time: 12:03 PM
 * To change this template use File | Settings | File Templates.
 */
public abstract class SessionState {


    protected JTable table;
    protected MediatorGUI mediator;
    protected ActionListener actionListener;
    protected String name;


    protected static final String[] columnNames = {"Service Name",
            "Users List",
            "Auction Status",
            "Download Progress"};


    public SessionState() {

    }

    public SessionState(String name, List<String> services, ActionListener actionListener, MediatorGUI mediator) {

        this.actionListener = actionListener;
        this.mediator = mediator;
        this.name = name;

        MyTableModel tableModel = new MyTableModel(columnNames,
                services, getListMouseListener(), getTableMouseListener());
        this.table = tableModel.createJTable();


    }

    protected abstract PopupListener getTableMouseListener();

    protected abstract PopupListener getListMouseListener();


    protected PopupListener createMouseListener(PopupType popupType, MenuItemType type1, MenuItemType type2) {

        JMenuItem menuItem;
        JPopupMenu popupMenu = new JPopupMenu();

        menuItem = new CommandMenuItem(type1, mediator);
        menuItem.addActionListener(actionListener);
        popupMenu.add(menuItem);
        menuItem = new CommandMenuItem(type2, mediator);
        menuItem.addActionListener(actionListener);
        popupMenu.add(menuItem);

        popupMenu.setLightWeightPopupEnabled(false);

        return PopupListener.popupListenerFactory(popupType, popupMenu);
    }

    public JTable getTable() {
        return table;
    }

    protected void inquire(int row) {
        List<String> users = mediator.inquireService((String) table.getModel().getValueAt(row, MyTableModel.SERVICE_NAME_COLUMN));

        DefaultListModel listModel = (DefaultListModel) table.getModel().getValueAt(row, MyTableModel.USER_LIST_COLUMN);

        for (String user : users) {
            Auction element = new Auction(user);

            if (!listModel.contains(element)) {

                listModel.addElement(element);
            }
        }
        table.repaint();
    }


    public void addUser(String username, String type, List<String> services) {

        TableModel model = table.getModel();
        int rowCount = model.getRowCount();
        DefaultListModel listModel;


        if (type.equalsIgnoreCase(name))
            return;

        for (int row = 0; row < rowCount; row++) {
            listModel = (DefaultListModel) model.getValueAt(row, MyTableModel.USER_LIST_COLUMN);

            Auction element = new Auction(username);

            if (canAddUser(element, services, model, listModel, row)) {

                listModel.addElement(element);

            }

            verifyStatus(row);
        }

        table.repaint();


    }

    private boolean canAddUser(Auction element, List<String> services, TableModel model, DefaultListModel listModel, int row) {
        return canAddUser(model, row) &&
                services.contains(model.getValueAt(row, MyTableModel.SERVICE_NAME_COLUMN)) &&
                !listModel.contains(element);
    }

    protected int getServiceRow(String service) {
        for (int i = 0; i < table.getRowCount(); i++) {
            String s = (String) table.getModel().getValueAt(i, SERVICE_NAME_COLUMN);
            if (service.equals(s))
                return i;
        }
        return -1;
    }


    public void removeUser(String name, String type) {

        TableModel model = table.getModel();
        int rowCount = model.getRowCount();
        DefaultListModel listModel;



        if (type.equalsIgnoreCase(this.name))
            return;

        for (int row = 0; row < rowCount; row++) {

            listModel = (DefaultListModel) model.getValueAt(row, USER_LIST_COLUMN);
            listModel.removeElement(new Auction(name));
            resetTransfer(name, model, row);

            verifyStatus(row);

        }

        table.repaint();
    }

    protected void resetTransfer(String name, TableModel model, int row) {
        Service service = (Service) model.getValueAt(row, PROGRESS_BAR_COLUMN);

        if (name.equals(service.getSourceUser())) {
            model.setValueAt(new ServiceImpl(), row, PROGRESS_BAR_COLUMN);
        }
    }


    public abstract void launchOffer(int row);

    public abstract void dropOffer(int row);

    public abstract void login();


    public abstract void auctionStatusChanged(String service, Auction auction);

    protected abstract void verifyStatus(int row);

    protected abstract boolean canAddUser(TableModel model, int row);


}
