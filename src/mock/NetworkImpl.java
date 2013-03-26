package mock;

import app.Network;
import mediator.MediatorNetwork;
import models.Service;
import models.ServiceImpl;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: nogai
 * Date: 3/24/13
 * Time: 11:00 PM
 * To change this template use File | Settings | File Templates.
 */
public class NetworkImpl implements Network, Runnable {

    MediatorNetwork mediator;
    Map<String,List<String>> user_services = new HashMap<String, List<String>>();


    public NetworkImpl(MediatorNetwork mediator) {
        this.mediator = mediator;
        mediator.registerNetwork(this);

        List<String> services = new LinkedList<String>();
        services.add("serviciu3");
        services.add("serviciu5");

        user_services.put("Gogu",services);

        services = new LinkedList<String>();
        services.add("serviciu9");
        services.add("serviciu10");

        user_services.put("Lola",services);


    }

    @Override
    public Service purchaseService(String name) {
        return new ServiceImpl();
    }

    @Override
    public void offerLaunched(String username, String service) {
        System.out.println("[Network]Launching offer for service " + service + " from user " + username);
    }

    @Override
    public void removeUserFromLists(String username, String type) {
        System.out.println("[Network]Removing user " + username + " from all lists");

        mediator.userLoggedOut(username,type);
    }

    @Override
    public void offerDropped(String username, String service) {
        System.out.println("[Network]Dropping offer for service " + service + " from user " + username);
    }

    @Override
    public void newUser(String username, String type, List<String> services) {
        System.out.println("[Network]New " + type + " has entered the system: " + username);

        mediator.userLoggedIn(username, type, services);
    }

    @Override
    public List<String> inquireService(String service) {

        List<String> users = new LinkedList<String>();

        for(Map.Entry<String,List<String>> entry:user_services.entrySet()){
            if(entry.getValue().contains(service))
                users.add(entry.getKey());
        }

        return users;


    }

    @Override
    public void run() {

        List<String> services = new LinkedList<String>();
        services.add("serviciu4");
        services.add("serviciu8");

        sleep();

        newUser("Relu", "seller", services);
        newUser("Bicu", "buyer", services);

        sleep();
        services.add("serviciu1");
        services.add("serviciu2");


        newUser("Miki", "seller", services);
        newUser("Kiki", "buyer", services);

        newUser("Shiki", "seller", services);
        newUser("Biki VickiShi Mooul", "buyer", services);


        sleep();

        removeUserFromLists("Relu", "seller");
        removeUserFromLists("Bicu", "buyer");

        sleep();

        removeUserFromLists("Kiki", "buyer");
        removeUserFromLists("Miki", "seller");

        removeUserFromLists("Shiki", "seller");
        removeUserFromLists("Biki VickiShi Mooul", "buyer");


    }

    private void sleep() {
        try {
            Thread.sleep(10000);
        } catch (InterruptedException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
    }
}
