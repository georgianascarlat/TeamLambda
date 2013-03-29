package mock;

import app.Network;
import mediator.MediatorNetwork;
import models.Auction;
import models.StatusTypes;

import java.util.*;

/**
 * Created with IntelliJ IDEA.
 * User: nogai
 * Date: 3/24/13
 * Time: 11:00 PM
 * To change this template use File | Settings | File Templates.
 */
public class NetworkImpl implements Network, Runnable {

    MediatorNetwork mediator;
    Map<String, List<String>> user_services = new HashMap<String, List<String>>();
    String userType;


    public NetworkImpl(MediatorNetwork mediator,String userType) {
        this.mediator = mediator;
        this.userType = userType;
        mediator.registerNetwork(this);

        List<String> services = new LinkedList<String>();
        services.add("serviciu3");
        services.add("serviciu5");

        user_services.put("Gogu", services);

        services = new LinkedList<String>();
        services.add("serviciu9");
        services.add("serviciu10");

        user_services.put("Lola", services);


    }


    @Override
    public void offerLaunched(String username, String service) {
        System.out.println("[Network]Launching offer for service " + service + " from user " + username);
    }

    @Override
    public void removeUserFromLists(String username, String type) {
        System.out.println("[Network]Removing user " + username + " from all lists");

        mediator.userLoggedOut(username, type);
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

        for (Map.Entry<String, List<String>> entry : user_services.entrySet()) {
            if (entry.getValue().contains(service))
                users.add(entry.getKey());
        }

        return users;


    }

    @Override
    public void auctionStatusChangeRequest(String sourceUser, String sourceUserType, String service, Auction auction) {
        System.out.println("[Network]Auction status changed by " + sourceUser + " for service " + service + " into " + auction.getStatus());

    }

    @Override
    public void auctionStatusChangeInform(String sourceUser, String sourceUserType, String service, Auction auction) {

        mediator.auctionStatusChangeInform(service, auction);
    }


    @Override
    public void run() {

        List<String> services = new LinkedList<String>();
        services.add("serviciu4");
        services.add("serviciu8");

        sleep();
        sleep();

        newUser("Relu", "seller", services);
        newUser("Nicu", "buyer", services);

        sleep();

        services.add("serviciu1");
        services.add("serviciu2");


        newUser("Miki", "seller", services);
        newUser("Kiki", "buyer", services);

        newUser("Shiki", "seller", services);
        newUser("Biki", "buyer", services);


        sleep();


        if(userType.equals("buyer")){

            user_services.put("Relu",services);
            user_services.put("Miki",services);
            user_services.put("Shiki",services);


            auctionStatusChangeInform("Ana", "buyer", "serviciu4", new Auction("Relu", StatusTypes.Offer_Made, 12));
            auctionStatusChangeInform("Ana", "buyer", "serviciu1", new Auction("Miki", StatusTypes.Offer_Made, 54));

            sleep();

            auctionStatusChangeInform("Ana", "buyer", "serviciu1", new Auction("Shiki", StatusTypes.Offer_Made, 60));
            auctionStatusChangeInform("Ana", "buyer", "serviciu4", new Auction("Shiki", StatusTypes.Offer_Made, 60));

            sleep();
            sleep();

            removeUserFromLists("Relu", "seller");
            sleep();


            removeUserFromLists("Miki", "seller");
            removeUserFromLists("Shiki", "seller");


        }

        if(userType.equals("seller")){
            user_services.put("Nicu",services);
            user_services.put("Kiki",services);
            user_services.put("Biki",services);
        }

//        List<String> services = new LinkedList<String>();
//        services.add("serviciu4");
//        services.add("serviciu8");
//
//        sleep();
//
//        newUser("Relu", "seller", services);
//        newUser("Bicu", "buyer", services);
//
//        sleep();
//        services.add("serviciu1");
//        services.add("serviciu2");
//
//
//        newUser("Miki", "seller", services);
//        newUser("Kiki", "buyer", services);
//
//        newUser("Shiki", "seller", services);
//        newUser("Biki", "buyer", services);
//
//
//        sleep();
//
//        //auctionStatusChangeInform("Ana", "buyer", "serviciu4", new Auction("Relu", StatusTypes.Offer_Made, 12));
//
//        //auctionStatusChangeInform("Ana", "buyer", "serviciu1", new Auction("Miki", StatusTypes.Offer_Made, 54));
//
//        //sleep();
//
//        //auctionStatusChangeInform("Ana", "buyer", "serviciu1", new Auction("Miki", StatusTypes.Inactive, 12));
//
//        auctionStatusChangeInform("Ana", "seller", "serviciu1", new Auction("Biki", StatusTypes.Offer_Accepted, 12));
//        auctionStatusChangeInform("Ana", "seller", "serviciu1", new Auction("Kiki", StatusTypes.Offer_Accepted, 12));
//        auctionStatusChangeInform("Ana", "seller", "serviciu4", new Auction("Bicu", StatusTypes.Offer_Accepted, 12));
//
//
//        sleep();
//
//        removeUserFromLists("Relu", "seller");
//        //removeUserFromLists("Bicu", "buyer");
//
//        sleep();
//        sleep();
//
//        //removeUserFromLists("Kiki", "buyer");
//        removeUserFromLists("Miki", "seller");
//
//        removeUserFromLists("Shiki", "seller");
//        //removeUserFromLists("Biki ", "buyer");


    }

    private void sleep() {
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
    }
}
