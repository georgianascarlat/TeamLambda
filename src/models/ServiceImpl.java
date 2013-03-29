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
    private String name;
    private StatusTypes status;
    private String sourceUser;

    public ServiceImpl() {

        name = "";
        sourceUser = "";
        progress = 0;
        status = StatusTypes.Transfer_Started;
    }

    public ServiceImpl(String serviceName, StatusTypes status, String sourceUser) {
        this.name = serviceName;
        this.status = status;
        this.progress = 0;
        this.sourceUser = sourceUser;
    }

    @Override
    public StatusTypes getPurchaseStatus() {
        return status;
    }

    @Override
    public void setPurchaseProgress(int progress) {
        this.progress = progress;
    }

    @Override
    public int getPurchaseProgress() {

        return progress;
    }

    @Override
    public void setPurchaseStatus(StatusTypes status) {
        this.status = status;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public String getSourceUser() {
        return sourceUser;
    }
}
