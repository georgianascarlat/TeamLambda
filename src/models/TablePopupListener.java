package models;

import commands.CommandMenuItem;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.util.Arrays;

/**
 * Created with IntelliJ IDEA.
 * User: nogai
 * Date: 3/25/13
 * Time: 10:35 PM
 * To change this template use File | Settings | File Templates.
 */
public class TablePopupListener extends PopupListener {

    @Override
    protected void passPopupInfo(MouseEvent e) {

        int index;
        JTable table = (JTable) e.getComponent();
        CommandMenuItem menuItem;
        Component[] components = popup.getComponents();

        index = table.rowAtPoint(e.getPoint());
        if (index < 0)
            return;

        for (Component component : components) {

            menuItem = (CommandMenuItem) component;
            menuItem.setSelectedService((String) table.getModel().getValueAt(index, MyTableModel.SERVICE_NAME_COLUMN));
            menuItem.setSelectedServiceRow(index);
            menuItem.setStatusType((StatusTypes) table.getModel().getValueAt(index, MyTableModel.STATUS_COLUMN));
        }
    }


    public TablePopupListener(JPopupMenu popupMenu) {
        super(popupMenu);
    }
}
