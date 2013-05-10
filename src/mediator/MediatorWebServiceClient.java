package mediator;

import app.WebServiceClient;

/**
 * The mediator interface visible from the Web Service Client.
 */
public interface MediatorWebServiceClient {

    /**
     * Register web service client.
     *
     * @param webServiceClient  web service client
     */
    public void registerWebServiceClient(WebServiceClient webServiceClient);


}
