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
    private static int count = 0;


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
    public void auctionStatusChangeRequest(String sourceUser, String sourceUserType, String service, Auction auction) {
        System.out.println("[Network]Auction status changed by " + sourceUser + " for service " + service + " into " + auction.getStatus());


        if(StatusTypes.Offer_Made.equals(auction.getStatus()) && "seller".equals(sourceUserType)){
            switch (count){
                case 0:
                    mediator.auctionStatusChangeInform(service,new Auction(auction.getUser(),StatusTypes.Offer_Accepted,auction.getPrice()));
                    break;
                case 1:
                    mediator.auctionStatusChangeInform(service,new Auction(auction.getUser(),StatusTypes.Offer_Exceeded,auction.getPrice()));
                    break;
                default:
                    mediator.auctionStatusChangeInform(service,new Auction(auction.getUser(),StatusTypes.Offer_Refused,auction.getPrice()));
                    break;
            }

            count = (count +1)%3;

        }

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

            sleep();
            sleep();

            removeUserFromLists("Nicu", "buyer");
            sleep();


            removeUserFromLists("Kiki", "buyer");
            removeUserFromLists("Biki", "buyer");
        }


    }

    private void sleep() {
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
    }
}
