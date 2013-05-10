package webclient;

import models.*;
import app.WebServiceClient;

import javax.xml.rpc.ServiceException;
import java.rmi.RemoteException;
import java.util.*;


public class WebServiceClientImpl implements WebServiceClient {



    private LambdaWebService_PortType service;

    public WebServiceClientImpl() throws ServiceException {

        LambdaWebServiceServiceLocator locator = new LambdaWebServiceServiceLocator();
        service = locator.getLambdaWebService();
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
    public List<String> getRelevantUsers(String type, String serviceName) {

        List<String> result;

        try {
            String users[] =  service.relevantUsers(type,serviceName);
            if(users == null){
                result = new LinkedList<String>();
            } else{
                result = Arrays.asList(users);
            }

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
