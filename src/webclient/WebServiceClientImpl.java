package webclient;

import models.*;
import app.WebServiceClient;
import webclient.LambdaWebServiceServiceLocator;
import webclient.LambdaWebService_PortType;

import javax.xml.rpc.ServiceException;
import java.rmi.RemoteException;
import java.util.*;

/**
 * Created with IntelliJ IDEA.
 * User: nogai
 * Date: 3/24/13
 * Time: 7:01 PM
 * To change this template use File | Settings | File Templates.
 */
public class WebServiceClientImpl implements WebServiceClient {


    private List<Service> servicesInProgress;
    private LambdaWebService_PortType service;

    public WebServiceClientImpl() throws ServiceException {

        LambdaWebServiceServiceLocator locator = new LambdaWebServiceServiceLocator();
        service = locator.getLambdaWebService();

        this.servicesInProgress = new LinkedList<Service>();
    }


    @Override
    public boolean addUserToDB(LoginInfo loginInfo) {
        boolean  ok;

        try {
            ok = service.loginUser(loginInfo.getUsername(),loginInfo.getType(),loginInfo.getPassword(), loginInfo.getServiceNames().toArray(new String[0]));
        } catch (RemoteException e) {
            e.printStackTrace();
            ok = false;
        }

        return ok;
    }

    @Override
    public synchronized Service serviceTransfer(String username, String serviceName, Auction auction) {

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
    public List<String> getRelevantUsers(String type, String serviceName) {

        List<String> result;

        try {
            String users[] =  service.relevantUsers(type,serviceName);
            result = Arrays.asList(users);
        } catch (RemoteException e) {
            e.printStackTrace();
            result = null;
        }

        return result;
    }

    @Override
    public void makeUserInactive(String username) {

        try {
            service.logoutUser(username);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }
}
