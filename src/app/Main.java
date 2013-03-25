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

        MediatorGUI mediator = new Mediator();
        mediator.registerWebServiceClient(new WebServiceClientImpl());
        mediator.registerNetwork(new NetworkImpl());


        new GUIImpl(mediator);
    }
}
