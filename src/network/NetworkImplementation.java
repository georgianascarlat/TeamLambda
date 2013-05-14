package network;

import app.Network;
import mediator.MediatorNetwork;
import models.Auction;
import models.Service;
import models.ServiceImpl;
import models.StatusTypes;

import java.util.List;

public class NetworkImplementation implements Network {
	
    private MediatorNetwork mediator;
    private Client netClient = null;
    private Thread netThread = null;

    public NetworkImplementation(MediatorNetwork mediator) {
        this.mediator = mediator;
        mediator.registerNetwork(this);
    }

	@Override
	public void newUser(String username, String type, List<String> services) {
		netClient = new Client(username, type, services, mediator);
		netThread = new Thread(netClient);
		netThread.start();
	}
	
    @Override
    public void auctionStatusChangeInform(String sourceUser, String sourceUserType, String service, Auction auction) {
        mediator.auctionStatusChangeInform(service, auction);
    }

	@Override
	public void auctionStatusChangeRequest(String sourceUser, String sourceUserType, String service, Auction auction) {
		try {
			netClient.send(auction.getUser() + " " + sourceUser + " " + service + " " + auction.getStatus() + " " + auction.getPrice(), null, NetworkConstants.AUCTION_CHANGE_REQUEST);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		mediator.auctionStatusChangeInform(service, new Auction(sourceUser, auction.getStatus(), auction.getPrice()));		
	}
        
	@Override
	public void removeUserFromLists(String username, String type) {
    	try {
    		netClient.send(username + " " + type, null, NetworkConstants.LOGOUT_MESSAGE);
        	netClient.terminate();
			netThread.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}    	
	}
    
	@Override
	public void offerLaunched(String username, String service) {
		try {
			netClient.send(username + " " + service, null, NetworkConstants.OFFER_LAUNCHED);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}		
	}

	@Override
	public void offerDropped(String username, String service) {
		try {
			netClient.send(username + " " + service, null, NetworkConstants.OFFER_DROPPED);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}


	@Override
	public void startServiceTransferRequest(String username, String service, Auction auction) {
		try {
			netClient.send(auction.getUser() + " " + username + " " + service + " " + auction.getPrice(), service, NetworkConstants.START_TRANSFER);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		mediator.auctionStatusChangeInform(service, new Auction(username, StatusTypes.Transfer_Started, auction.getPrice()));		
	}


	@Override
	public Service serviceStatusRequest(String username, String service, Auction auction) {
		ServiceImpl s = new ServiceImpl(service, StatusTypes.Transfer_In_Progress, username);
		int progress = netClient.getSendProgress();
		if (progress == 100)
			s.setPurchaseStatus(StatusTypes.Transfer_Completed);
		s.setPurchaseProgress(progress);
		return s;
	}

	
}
