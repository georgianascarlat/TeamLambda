package mock;

import app.Network;
import mediator.MediatorNetwork;
import models.Auction;
import models.Service;
import models.ServiceImpl;
import models.StatusTypes;

import java.util.*;


public class NetworkImplMock implements Network, Runnable {

    private MediatorNetwork mediator;
    private Map<String, List<String>> user_services = new HashMap<String, List<String>>();

    private static int count = 0;
    private List<Service> servicesInProgress;


    public NetworkImplMock(MediatorNetwork mediator) {
        this.mediator = mediator;

        this.servicesInProgress = new LinkedList<Service>();
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


        if (StatusTypes.Offer_Made.equals(auction.getStatus()) && "seller".equals(sourceUserType)) {
            switch (count) {
                case 0:
                    mediator.auctionStatusChangeInform(service, new Auction(auction.getUser(), StatusTypes.Offer_Accepted, auction.getPrice()));
                    break;
                case 1:
                    mediator.auctionStatusChangeInform(service, new Auction(auction.getUser(), StatusTypes.Offer_Exceeded, auction.getPrice()));
                    break;
                default:
                    mediator.auctionStatusChangeInform(service, new Auction(auction.getUser(), StatusTypes.Offer_Refused, auction.getPrice()));
                    break;
            }

            count = (count + 1) % 3;

        }

        if (StatusTypes.Offer_Accepted.equals(auction.getStatus()) && "buyer".equals(sourceUserType)) {

            mediator.startServiceTransferInform(service, new Auction(auction.getUser(), StatusTypes.Offer_Accepted, auction.getPrice()));

        }

    }

    @Override
    public void startServiceTransferRequest(String username, String serviceName, Auction auction) {

    }

    @Override
    public void auctionStatusChangeInform(String sourceUser, String sourceUserType, String service, Auction auction) {

        mediator.auctionStatusChangeInform(service, auction);
    }

    @Override
    public Service serviceStatusRequest(String username, String serviceName, Auction auction) {


        int progress;
        Service service;

        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        for (Iterator<Service> it = servicesInProgress.iterator(); it.hasNext(); ) {
            service = it.next();
            if (service.getName().equals(serviceName)) {

                progress = service.getPurchaseProgress();
                if (progress >= 100) {

                    service.setPurchaseStatus(StatusTypes.Transfer_Completed);
                    service.setPurchaseProgress(100);
                    it.remove();

                } else {
                    service.setPurchaseStatus(StatusTypes.Transfer_In_Progress);
                    service.setPurchaseProgress(progress + 5);
                }

                return service;
            }
        }

        service = new ServiceImpl(serviceName, StatusTypes.Transfer_Started, auction.getUser());
        servicesInProgress.add(service);
        return service;
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


        auctionStatusChangeInform("haru", "buyer", "serviciu4", new Auction("Relu", StatusTypes.Offer_Made, 12));
        auctionStatusChangeInform("haru", "buyer", "serviciu1", new Auction("Miki", StatusTypes.Offer_Made, 54));

        sleep();

        auctionStatusChangeInform("haru", "buyer", "serviciu1", new Auction("Shiki", StatusTypes.Offer_Made, 60));
        auctionStatusChangeInform("haru", "buyer", "serviciu4", new Auction("Shiki", StatusTypes.Offer_Made, 60));

        sleep();
        sleep();

        removeUserFromLists("Relu", "seller");
        sleep();


        removeUserFromLists("Miki", "seller");
        removeUserFromLists("Shiki", "seller");


        sleep();
        sleep();

        removeUserFromLists("Nicu", "buyer");
        sleep();


        removeUserFromLists("Kiki", "buyer");
        removeUserFromLists("Biki", "buyer");


    }

    private void sleep() {
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
    }
}
