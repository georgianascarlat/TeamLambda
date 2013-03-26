package models;

import javax.swing.*;
import javax.swing.table.TableCellEditor;
import java.awt.*;
import java.awt.event.MouseListener;

/**
 * Created with IntelliJ IDEA.
 * User: nogai
 * Date: 3/25/13
 * Time: 4:42 PM
 * To change this template use File | Settings | File Templates.
 */
public class ListCellEditor extends AbstractCellEditor implements TableCellEditor {

    private ListModel currentValue;
    private PopupListener mouseListener;

    public ListCellEditor(PopupListener listMouseListener) {
        this.mouseListener = listMouseListener;
    }

    @Override
    public Component getTableCellEditorComponent(JTable jTable, Object o, boolean b, int i, int i1) {


        this.currentValue = (ListModel) o;



        if (o != null) {
            JList list = new JList((ListModel) o);


            JScrollPane pane = new JScrollPane(list);
            list.setSelectionBackground(Color.white);
            list.setCellRenderer(new MyListCellRenderer());
            list.addMouseListener(mouseListener);
            mouseListener.selectRow(jTable,i);

            return pane;
        }


        return null;
    }

    @Override
    public Object getCellEditorValue() {
        return currentValue;
    }
}
