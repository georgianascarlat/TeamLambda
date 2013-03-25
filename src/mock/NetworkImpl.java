package mock;

import app.Network;
import models.Service;
import models.ServiceImpl;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: nogai
 * Date: 3/24/13
 * Time: 11:00 PM
 * To change this template use File | Settings | File Templates.
 */
public class NetworkImpl implements Network {


    @Override
    public Service purchaseService(String name) {
        return new ServiceImpl();
    }

    @Override
    public void offerLaunched(String username, String service) {
        System.out.println("[Network]Launching offer for service " + service + " from user " + username);
    }

    @Override
    public void removeUserFromLists(String username) {
        System.out.println("[Network]Removing user " + username + " from all lists");
    }

    @Override
    public void offerDropped(String username, String service) {
        System.out.println("[Network]Dropping offer for service " + service + " from user " + username);
    }

    @Override
    public void newUser(String username, List<String> services) {
        System.out.println("[Network]New user has entered the system: "+username);
    }
}
