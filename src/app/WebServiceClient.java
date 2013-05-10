package app;

import exceptions.NoSuchUserException;
import models.Auction;
import models.LoginInfo;
import models.Service;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: nogai
 * Date: 3/24/13
 * Time: 7:00 PM
 * To change this template use File | Settings | File Templates.
 */
public interface WebServiceClient {

    public boolean addUserToDB(LoginInfo loginInfo);

    public List<String> getRelevantUsers(String type, String serviceName);

    public void makeUserInactive(String username);
}
