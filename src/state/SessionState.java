package state;

import mediator.MediatorGUI;
import commands.CommandMenuItem;
import models.*;

import javax.swing.*;
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


    protected static final String[] columnNames = {"Service Name",
            "Users List",
            "Auction Status",
            "Download Progress"};


    public SessionState(List<String> services, ActionListener actionListener, MediatorGUI mediator) {

        this.actionListener = actionListener;
        this.mediator = mediator;

        MyTableModel tableModel = new MyTableModel(columnNames,
                getDataFromServices(services), getListMouseListener(), getTableMouseListener());
        this.table = tableModel.createJTable();


    }

    protected abstract PopupListener getTableMouseListener();

    protected abstract PopupListener getListMouseListener();


    protected Object[][] getDataFromServices(List<String> services) {
        int size = services.size();
        Object[][] data = new Object[size][4];

        for (int i = 0; i < size; i++) {

            data[i][0] = services.get(i);
            data[i][1] = new DefaultListModel<String>();
            ((DefaultListModel<String>) data[i][1]).addElement("Ana");
            ((DefaultListModel<String>) data[i][1]).addElement("Bibi");
            ((DefaultListModel<String>) data[i][1]).addElement("Bibi");
            data[i][2] = StatusTypes.Inactive;
            data[i][3] = new ServiceImpl();

        }

        return data;
    }

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
}
