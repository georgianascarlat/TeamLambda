package models;

import javax.swing.*;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;
import java.awt.*;

/**
 * Created with IntelliJ IDEA.
 * User: nogai
 * Date: 3/24/13
 * Time: 6:51 PM
 * To change this template use File | Settings | File Templates.
 */
public class MyTableModel extends AbstractTableModel {
    private String[] columnNames;
    private Object[][] data;
    private static final int COLUMN_OFFSET = 12;
    private static final int PROGRESS_BAR_WIDTH = 300;

    public MyTableModel(String[] columnNames, Object[][] data) {
        this.columnNames = columnNames;
        this.data = data;
    }

    public int getColumnCount() {
        return columnNames.length;
    }

    public int getRowCount() {
        return data.length;
    }

    public String getColumnName(int col) {
        return columnNames[col];
    }

    public Object getValueAt(int row, int col) {
        return data[row][col];
    }

    public Class getColumnClass(int c) {
        Class cls = getValueAt(0, c).getClass();
        Class[] interfaces = cls.getInterfaces();
        if(interfaces.length > 0)
            return interfaces[0];

        return cls;
    }

    /*
     * Don't need to implement this method unless your table's
     * editable.
     */
    public boolean isCellEditable(int row, int col) {
        return false;
    }

    /*
     * Don't need to implement this method unless your table's
     * data can change.
     */
    public void setValueAt(Object value, int row, int col) {
        data[row][col] = value;
        fireTableCellUpdated(row, col);
    }

    public JTable createJTable() {

        JTable table;
        int height = 0, width;
        TableColumn col = null;
        Component comp;

        table = new JTable(this);

        table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        ListRenderer lr = new ListRenderer();
        table.setDefaultRenderer(DefaultListModel.class, lr);

        ProgressBarRenderer pr = new ProgressBarRenderer();
        table.setDefaultRenderer(Service.class, pr);


        for (int i = 0; i < table.getColumnCount(); i++) {

            col = table.getColumnModel().getColumn(i);
            width = 0;


            TableCellRenderer renderer = table.getTableHeader().getDefaultRenderer();
            comp = renderer.getTableCellRendererComponent(
                    null, col.getHeaderValue(),
                    false, false, 0, 0);
            width = Math.max(width, comp.getPreferredSize().width);

            for (int r = 0; r < table.getRowCount(); r++) {
                renderer = table.getCellRenderer(r, i);
                comp = renderer.getTableCellRendererComponent(table, table.getValueAt(r, i),
                        false, false, r, i);
                width = Math.max(width, comp.getPreferredSize().width);
                height = Math.max(height, comp.getPreferredSize().height);
            }
            col.setPreferredWidth(width + COLUMN_OFFSET);

        }

        if(col != null)
            col.setPreferredWidth(PROGRESS_BAR_WIDTH);

        table.setRowHeight(height);

        return table;
    }


}
