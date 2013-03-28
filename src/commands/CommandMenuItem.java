package commands;

import mediator.MediatorGUI;
import models.Auction;
import models.MenuItemType;
import models.StatusTypes;

import javax.swing.*;

import static models.StatusTypes.*;

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
    private String selectedService;
    private Auction selectedListElement;
    private int selectedServiceRow, selectedListElementIndex;
    private StatusTypes statusType = Inactive;

    public CommandMenuItem(MenuItemType type, MediatorGUI med) {
        super(type.getText());
        this.type = type;
        this.med = med;


    }

    public void setStatusType(StatusTypes statusType) {
        this.statusType = statusType;

        if (selectedListElementIndex < 0 || selectedServiceRow < 0) {
            this.setVisible(false);
            return;

        }

        this.setVisible(true);

        switch (type) {

            case LaunchOfferRequest:

                if (!statusType.equals(Inactive))
                    this.setVisible(false);

                break;
            case DropOfferRequest:

                if (statusType.equals(Inactive) || statusType.equals(Offer_Accepted))
                    this.setVisible(false);

                break;
            case AcceptOffer:

                break;
            case RefuseOffer:
                break;
            case MakeOffer:

                if (statusType.equals(Inactive))
                    this.setVisible(false);

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

    public void setSelectedListElement(Auction selectedListElement) {
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

                float  price = getPriceFromUser();

                selectedListElement.setStatus(StatusTypes.Offer_Made);
                selectedListElement.setPrice(price);

                med.makeOffer(selectedService, selectedServiceRow, selectedListElement, selectedListElementIndex);
                break;
            case DropAuction:
                // med.dropAuction();
                break;
        }
    }

    private float getPriceFromUser() {

        float price;
        String s, message = "Choose the price:\n";
        String  errorMessage = "Please insert a valid price!\n";
        int type = JOptionPane.PLAIN_MESSAGE;

        while(true){
            s = (String) JOptionPane.showInputDialog(
                    null,
                    message,
                    "Customized Dialog",
                    type,
                    null,
                    null,
                    "service price");

            try{
                price = Float.parseFloat(s);
                if(price > 0)
                    break;

            } catch (NumberFormatException e){
                message = errorMessage ;
                type = JOptionPane.ERROR_MESSAGE;
            }

        }
        return price;
    }

}
