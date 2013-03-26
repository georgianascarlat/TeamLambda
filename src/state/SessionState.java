package state;

import mediator.MediatorGUI;
import commands.CommandMenuItem;
import models.*;

import javax.swing.*;
import javax.swing.table.TableModel;
import java.awt.event.ActionListener;
import java.util.List;

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

    // se lanseaza o cerere de oferta
    public abstract void launchOffer(int row);


    public abstract void dropOffer(int row);

    public void newUserAppeared(String username, String type, List<String> services) {

        TableModel model = table.getModel();
        int rowCount = model.getRowCount();
        DefaultListModel listModel;


        if (type.equalsIgnoreCase(name))
            return;

        for (int row = 0; row < rowCount; row++) {
            if (canAddUser(model, row)&&
                    services.contains(model.getValueAt(row, MyTableModel.SERVICE_NAME_COLUMN))) {

                listModel = (DefaultListModel) model.getValueAt(row, MyTableModel.USER_LIST_COLUMN);
                listModel.addElement(username);

            }
        }

        table.repaint();


    }

    protected abstract boolean canAddUser(TableModel model, int row);


    public void removeUser(String name, String type) {

        TableModel model = table.getModel();
        int rowCount = model.getRowCount();
        DefaultListModel listModel;


        if (type.equalsIgnoreCase(name))
            return;

        for (int row = 0; row < rowCount; row++) {

            listModel = (DefaultListModel) model.getValueAt(row, MyTableModel.USER_LIST_COLUMN);
            listModel.removeElement(name);


        }

        table.repaint();
    }
}
