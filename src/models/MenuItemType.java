package models;

import exceptions.NoSuchUserTypeException;

import java.security.InvalidParameterException;

/**
 * Created with IntelliJ IDEA.
 * User: nogai
 * Date: 3/25/13
 * Time: 1:38 PM
 * To change this template use File | Settings | File Templates.
 */
public enum MenuItemType {

    LaunchOfferRequest("Launch Offer request"),
    DropOfferRequest("Drop Offer request"),
    AcceptOffer("Accept offer"),
    RefuseOffer("Refuse offer"),
    MakeOffer("Make offer"),
    DropAuction("Drop auction");

    private String text;

    private MenuItemType(String text) {
        this.text = text;
    }

    public String getText() {
        return text;
    }

    public static MenuItemType fromString(String text) throws InvalidParameterException {
        if (text != null) {
            for (MenuItemType b : MenuItemType.values()) {
                if (text.equalsIgnoreCase(b.text)) {
                    return b;
                }
            }
        }
        throw new InvalidParameterException(text);
    }
}
