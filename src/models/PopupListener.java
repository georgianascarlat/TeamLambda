package models;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/**
 * Created with IntelliJ IDEA.
 * User: nogai
 * Date: 3/25/13
 * Time: 3:32 PM
 * To change this template use File | Settings | File Templates.
 */
public class PopupListener extends MouseAdapter {
    JPopupMenu popup;

    public PopupListener(JPopupMenu popupMenu) {
        popup = popupMenu;
    }



    public void mousePressed(MouseEvent e) {
        maybeShowPopup(e);
    }

    public void mouseReleased(MouseEvent e) {
        maybeShowPopup(e);
    }

    private void maybeShowPopup(MouseEvent e) {

        Component component = e.getComponent();
        if (e.isPopupTrigger()) {
            popup.show(component, e.getX(), e.getY());


//            if(component instanceof JList){
//                JList list = (JList)component;
//
//                System.out.println(list.locationToIndex(e.getPoint()));
//            }
//
//            if(component instanceof JTable){
//                JTable table = (JTable)component;
//                System.out.println(table.rowAtPoint(e.getPoint()));
//            }

        }
    }

    @Override
    public String toString() {
        return "PopupListener{" +
                "popup=" + popup +
                '}';
    }
}
