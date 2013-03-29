package models;

import javax.swing.*;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;
import java.awt.*;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: nogai
 * Date: 3/24/13
 * Time: 6:51 PM
 * To change this template use File | Settings | File Templates.
 */
public class MyTableModel extends AbstractTableModel {
    public static final int USER_LIST_COLUMN = 1;
    public static final int PROGRESS_BAR_COLUMN = 3;
    public static final int COLUMN_NUMBER = 4;

    public static final int COLUMN_OFFSET = 12;
    public static final int PROGRESS_BAR_WIDTH = 200;
    public static final int STATUS_COLUMN = 2;
    public static final int SERVICE_NAME_COLUMN = 0;
    public static final int ROW_HEIGHT = 50;
    private static final int USER_LIST_WIDTH = 150;
    public static final int LIST_ELEM_HEIGHT = 25;

    private Object[][] data;
    private String[] columnNames;
    private List<String> services;
    private PopupListener listMouseListener;
    private PopupListener tableMouseListener;


    public MyTableModel(String[] columnNames, List<String> services, PopupListener listMouseListener, PopupListener tableMouseListener) {
        this.columnNames = columnNames;
        this.services = services;
        this.data = getDataFromServices(services);
        this.listMouseListener = listMouseListener;
        this.tableMouseListener = tableMouseListener;
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
        if (interfaces.length > 0)
            return interfaces[0];

        return cls;
    }

    /*
     * Don't need to implement this method unless your table's
     * editable.
     */
    public boolean isCellEditable(int row, int col) {

        return getColumnClass(col).equals(DefaultListModel.class);
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

        JTable table = new JTable(this);

        table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        ListRenderer lr = new ListRenderer();

        table.setDefaultRenderer(DefaultListModel.class, lr);

        table.setDefaultEditor(DefaultListModel.class, new ListCellEditor(listMouseListener));
        table.addMouseListener(tableMouseListener);

        ProgressBarRenderer pr = new ProgressBarRenderer();

        table.setDefaultRenderer(Service.class, pr);


        adjustTableDimensions(table);

        return table;
    }

    private void adjustTableDimensions(JTable table) {
        int width;
        TableColumn col = null;
        Component comp;

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

            }
            col.setPreferredWidth(width + COLUMN_OFFSET);

            if (i == USER_LIST_COLUMN)
                col.setPreferredWidth(USER_LIST_WIDTH);

        }

        if (col != null)
            col.setPreferredWidth(PROGRESS_BAR_WIDTH);

        table.setRowHeight(ROW_HEIGHT);
    }

    private Object[][] getDataFromServices(java.util.List<String> services) {
        int size = services.size();
        Object[][] data = new Object[size][COLUMN_NUMBER];

        for (int i = 0; i < size; i++) {

            data[i][SERVICE_NAME_COLUMN] = services.get(i);
            data[i][USER_LIST_COLUMN] = new DefaultListModel<String>();
            data[i][STATUS_COLUMN] = StatusTypes.Inactive;
            data[i][PROGRESS_BAR_COLUMN] = new ServiceImpl();

        }

        return data;
    }


}
