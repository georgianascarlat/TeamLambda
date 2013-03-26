package models;

import javax.swing.*;
import javax.swing.table.TableCellRenderer;
import java.awt.*;
import java.awt.event.MouseListener;

/**
 * Created with IntelliJ IDEA.
 * User: nogai
 * Date: 3/24/13
 * Time: 10:01 PM
 * To change this template use File | Settings | File Templates.
 */
public class ListRenderer extends JList implements TableCellRenderer {


    @Override
    public Component getTableCellRendererComponent(JTable jTable, Object cellObject,
                                                   boolean isSelected, boolean hasFocus,
                                                   int row, int column) {

        if (cellObject != null)
            this.setModel((ListModel) cellObject);


        return this;
    }
}
