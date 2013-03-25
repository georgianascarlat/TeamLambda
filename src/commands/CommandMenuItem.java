package commands;

import mediator.MediatorGUI;
import models.MenuItemType;
import models.StatusTypes;

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
    private String selectedService, selectedListElement;
    private int selectedServiceRow, selectedListElementIndex;
    private StatusTypes statusType = StatusTypes.Inactive;

    public CommandMenuItem(MenuItemType type, MediatorGUI med) {
        super(type.getText());
        this.type = type;
        this.med = med;


    }

    public void setStatusType(StatusTypes statusType) {
        this.statusType = statusType;

        this.setVisible(true);

        switch (type) {

            case LaunchOfferRequest:
                if (!statusType.equals(StatusTypes.Inactive))
                    this.setVisible(false);
                break;
            case DropOfferRequest:
                if (statusType.equals(StatusTypes.Inactive) || statusType.equals(StatusTypes.Offer_Accepted))
                    this.setVisible(false);
                break;
            case AcceptOffer:
                break;
            case RefuseOffer:
                break;
            case MakeOffer:
                break;
            case DropAuction:
                break;
        }
    }

    public void setSelectedListElementIndex(int selectedListElementIndex) {
        this.selectedListElementIndex = selectedListElementIndex;
    }

    public void setSelectedServiceRow(int selectedServiceRow) {
        this.selectedServiceRow = selectedServiceRow;
    }

    public void setSelectedService(String selectedService) {
        this.selectedService = selectedService;
    }

    public void setSelectedListElement(String selectedListElement) {
        this.selectedListElement = selectedListElement;
    }

    @Override
    public void execute() {


        switch (type) {

            case LaunchOfferRequest:

                med.launchOffer(selectedService, selectedServiceRow);

                break;
            case DropOfferRequest:

                med.dropOffer(selectedService, selectedServiceRow);

                break;
            case AcceptOffer:
                // med.acceptOffer();
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
