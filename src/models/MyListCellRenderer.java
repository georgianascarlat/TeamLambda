package models;

import javax.swing.*;
import java.awt.*;

/**
 * Created with IntelliJ IDEA.
 * User: nogai
 * Date: 3/26/13
 * Time: 3:13 PM
 * To change this template use File | Settings | File Templates.
 */
public class MyListCellRenderer implements ListCellRenderer {

    @Override
    public Component getListCellRendererComponent(
            JList list,
            Object value,
            int index,
            boolean isSelected,
            boolean cellHasFocus) {

        JPanel panel = new JPanel(new GridBagLayout());
        Auction auction = (Auction) value;

        if (isSelected)
            panel.setBackground(Color.CYAN);

        panel.setBorder(BorderFactory.createMatteBorder(
                index == 0 ? 1 : 0, 1, 1, 1, Color.BLACK));

        GridBagConstraints gbc = new GridBagConstraints();

        gbc.anchor = GridBagConstraints.NORTH;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        //gbc.insets = new Insets(4,4,4,4);

        // name
        gbc.weightx = 0;
        panel.add(new JLabel("" + auction.getUser()), gbc);

        // status
        gbc.weightx = 1;
        panel.add(new JLabel("" + auction.getStatus()), gbc);

        // price
        if(auction.getPrice() > 0 ) {
            gbc.weightx = 0;
            String cost = String.format("$%.2f",auction.getPrice());
            panel.add(new JLabel(cost), gbc);
        }



        return panel;
    }
}
