package state;

import app.MediatorGUI;
import exceptions.NoSuchUserTypeException;
import models.UserType;

import java.awt.event.ActionListener;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: nogai
 * Date: 3/25/13
 * Time: 12:13 PM
 * To change this template use File | Settings | File Templates.
 */
public class SessionStateFactory {

    public static SessionState createSessionState(String stateName, List<String> services, ActionListener actionListener, MediatorGUI mediator) throws NoSuchUserTypeException {
        UserType userType = UserType.fromString(stateName);

        switch (userType) {

            case BUYER:
                return new BuyerSessionState(services, actionListener, mediator);
            case SELLER:
                return new SellerSessionState(services, actionListener, mediator);
            default:
                throw new NoSuchUserTypeException(stateName);
        }

    }
}
