package models;

import commands.CommandMenuItem;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;

/**
 * Created with IntelliJ IDEA.
 * User: nogai
 * Date: 3/25/13
 * Time: 10:35 PM
 * To change this template use File | Settings | File Templates.
 */
public class ListPopupListener extends PopupListener {

    private String selectedService;
    private int selectedServiceRow;
    private StatusTypes statusType;


    @Override
    protected void passPopupInfo(MouseEvent e) {
        JList list = (JList) e.getComponent();
        Component[] components = popup.getComponents();
        CommandMenuItem menuItem;
        int index;

        for (Component component : components) {

            menuItem = ((CommandMenuItem) component);
            index = list.locationToIndex(e.getPoint());

            menuItem.setSelectedListElementIndex(index);
            menuItem.setSelectedListElement((String) list.getModel().getElementAt(index));
            menuItem.setSelectedService(selectedService);
            menuItem.setSelectedServiceRow(selectedServiceRow);
            menuItem.setStatusType(statusType);


        }
    }

    @Override
    protected void setSelectedStatusType(StatusTypes statusType) {

        this.statusType = statusType;
    }

    @Override
    protected void setSelectedService(String name) {
        this.selectedService = name;
    }

    @Override
    protected void setSelectedServiceRow(int row) {
        this.selectedServiceRow = row;
    }

    public ListPopupListener(JPopupMenu popupMenu) {
        super(popupMenu);
    }
}
