package models;

/**
 * Created with IntelliJ IDEA.
 * User: nogai
 * Date: 3/24/13
 * Time: 11:04 PM
 * To change this template use File | Settings | File Templates.
 */
public interface Service {

    public StatusTypes getPurchaseStatus();

    public void setPurchaseProgress(int progress);

    public int getPurchaseProgress();

    public void setPurchaseStatus(StatusTypes status);

    public String getName();

    public String getSourceUser();
}
