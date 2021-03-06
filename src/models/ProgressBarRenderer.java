package models;

import javax.swing.*;
import javax.swing.table.TableCellRenderer;
import java.awt.*;

/**
 * Created with IntelliJ IDEA.
 * User: nogai
 * Date: 3/24/13
 * Time: 10:54 PM
 * To change this template use File | Settings | File Templates.
 */
public class ProgressBarRenderer extends JProgressBar implements TableCellRenderer {


    @Override
    public Component getTableCellRendererComponent(JTable jTable, Object cellObject,
                                                   boolean isSelected, boolean hasFocus,
                                                   int row, int column) {

        if (cellObject != null) {
            this.setMaximum(0);
            this.setMaximum(100);
            this.setValue(((Service) cellObject).getPurchaseProgress());
        }
        return this;
    }
}
