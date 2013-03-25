package models;

import commands.CommandMenuItem;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.security.InvalidParameterException;

/**
 * Created with IntelliJ IDEA.
 * User: nogai
 * Date: 3/25/13
 * Time: 3:32 PM
 * To change this template use File | Settings | File Templates.
 */
public abstract class PopupListener extends MouseAdapter {
    JPopupMenu popup;


    public PopupListener(JPopupMenu popupMenu) {
        popup = popupMenu;
    }

    public void mousePressed(MouseEvent e) {
        maybeShowPopup(e);
        passPopupInfo(e);
    }

    protected abstract void passPopupInfo(MouseEvent e);

    protected void setSelectedService(String name) {
    }

    protected void setSelectedServiceRow(int row) {
    }

    protected void setSelectedStatusType(StatusTypes statusType) {
    }


    public void mouseReleased(MouseEvent e) {
        maybeShowPopup(e);
    }

    private void maybeShowPopup(MouseEvent e) {

        if (e.isPopupTrigger()) {
            popup.show(e.getComponent(), e.getX(), e.getY());
        }
    }

    public static PopupListener popupListenerFactory(PopupType type, JPopupMenu popupMenu) {

        switch (type) {

            case ListPopup:
                return new ListPopupListener(popupMenu);
            case TablePopup:
                return new TablePopupListener(popupMenu);
            default:
                throw new InvalidParameterException("Invalid PopupListener type " + type.toString());
        }

    }


}
