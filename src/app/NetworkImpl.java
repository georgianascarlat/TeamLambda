package app;

import models.Service;
import models.ServiceImpl;

/**
 * Created with IntelliJ IDEA.
 * User: nogai
 * Date: 3/24/13
 * Time: 11:00 PM
 * To change this template use File | Settings | File Templates.
 */
public class NetworkImpl implements Network {

    @Override
    public Service purchaseService(String name) {
        return new ServiceImpl();
    }
}
