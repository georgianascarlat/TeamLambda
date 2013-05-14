package app;

import mediator.Mediator;
import network.NetworkImplementation;
import webclient.WebServiceClientImpl;

import javax.xml.rpc.ServiceException;

/**
 * Created with IntelliJ IDEA.
 * User: nogai
 * Date: 3/24/13
 * Time: 7:12 PM
 * To change this template use File | Settings | File Templates.
 */
public class Main {


    public static void main(String[] args) {

        if(args.length != 1){
            System.out.println("Program receives a user type argument");
            System.exit(1);
        }

        Mediator mediator = new Mediator();
        Network network = new NetworkImplementation(mediator);

        try {
            mediator.registerWebServiceClient(new WebServiceClientImpl());
        } catch (ServiceException e) {
            e.printStackTrace();
            System.exit(1);
        }


        new GUIImpl(mediator,args[0]);




    }
}
