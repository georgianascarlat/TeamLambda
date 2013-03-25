package app;

import exceptions.NoSuchUserException;
import models.LoginInfo;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: nogai
 * Date: 3/24/13
 * Time: 7:00 PM
 * To change this template use File | Settings | File Templates.
 */
public interface WebServiceClient {

    public List<String> getServices(LoginInfo loginInfo) throws NoSuchUserException;
}
