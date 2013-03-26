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
        ListModel listModel = (ListModel) cellObject;
        int heigth, width;

        if (cellObject != null)  {

            this.setModel(listModel);
            this.setCellRenderer(new MyListCellRenderer());


            //adjustTableDimensions(jTable, row, column, listModel);


        }


        return this;
    }

    private void adjustTableDimensions(JTable jTable, int row, int column, ListModel listModel) {

        int heigth;
        int width;
        heigth = listModel.getSize() * MyTableModel.LIST_ELEM_HEIGHT;
        jTable.setRowHeight(row,Math.max(heigth,jTable.getRowHeight()));
        width = this.getPreferredSize().width;
        jTable.getColumnModel().getColumn(column).setPreferredWidth(Math.max(width,jTable.getColumnModel().getColumn(column).getWidth()));
    }


}
