package state;

import exceptions.NoSuchUserTypeException;
import models.UserType;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: nogai
 * Date: 3/25/13
 * Time: 12:13 PM
 * To change this template use File | Settings | File Templates.
 */
public class SessionStateFactory {

    public static SessionState createSessionState(String stateName,List<String> services) throws NoSuchUserTypeException {
        UserType userType = UserType.fromString(stateName);

        switch (userType) {

            case BUYER:
                return new BuyerSessionState(services);
            case SELLER:
                return new SellerSessionState(services);
            default:
                throw new NoSuchUserTypeException();
        }

    }
}
