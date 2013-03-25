package models;

/**
 * Created with IntelliJ IDEA.
 * User: nogai
 * Date: 3/24/13
 * Time: 11:04 PM
 * To change this template use File | Settings | File Templates.
 */
public class ServiceImpl implements Service {

    private int progress;

    public ServiceImpl() {

        progress = 0;
    }

    @Override
    public int getPurchaseProgress() {
        progress = progress < 100 ? (progress + 1) : progress;
        return progress;
    }
}
