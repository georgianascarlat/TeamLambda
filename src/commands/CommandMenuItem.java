package commands;

import app.MediatorGUI;
import models.MenuItemType;

import javax.swing.*;

/**
 * Created with IntelliJ IDEA.
 * User: nogai
 * Date: 3/25/13
 * Time: 1:38 PM
 * To change this template use File | Settings | File Templates.
 */
public class CommandMenuItem extends JMenuItem implements Command {

    private MenuItemType type;
    private MediatorGUI med;

    public CommandMenuItem(MenuItemType type, MediatorGUI med) {
        super(type.getText());
        this.type = type;
        this.med = med;

    }

    @Override
    public void execute() {

        switch (type) {

            case LaunchOfferRequest:
                med.launchOffer();
                break;
            case DropOfferRequest:
                //med.dropOffer();
                break;
            case AcceptOffer:
                 med.acceptOffer();
                break;
            case RefuseOffer:
                // med.refuseOffer();
                break;
            case MakeOffer:
                // med.makeOffer();
                break;
            case DropAuction:
                // med.dropAuction();
                break;
        }
    }
}
