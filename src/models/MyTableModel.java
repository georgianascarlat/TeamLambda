package models;

import javax.swing.table.AbstractTableModel;
import javax.swing.table.TableColumn;

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


}
