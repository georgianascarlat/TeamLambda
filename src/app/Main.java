package app;

import mediator.Mediator;
import mediator.MediatorGUI;
import mock.NetworkImpl;
import mock.WebServiceClientImpl;

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

        mediator.registerWebServiceClient(new WebServiceClientImpl());

        new GUIImpl(mediator);

        new Thread(new NetworkImpl(mediator,args[0])).start();


    }
}
