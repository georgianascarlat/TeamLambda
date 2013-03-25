package state;

import models.MyTableModel;
import models.ServiceImpl;
import models.StatusTypes;

import javax.swing.*;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: nogai
 * Date: 3/25/13
 * Time: 12:03 PM
 * To change this template use File | Settings | File Templates.
 */
public abstract class SessionState {

    private MyTableModel tableModel;
    private static final String[] columnNames = {"Service Name",
            "Users List",
            "Auction Status",
            "Download Progress"};



    protected SessionState(List<String> services) {
        this.tableModel = new MyTableModel(columnNames, getDataFromServices(services));
    }


    private Object[][] getDataFromServices(List<String> services) {
        int size = services.size();
        Object[][] data = new Object[size][4];

        for(int i=0;i<size;i++){

            data[i][0] = services.get(i);
            data[i][1] = new DefaultListModel<String>();
            data[i][2] = StatusTypes.Inactive;
            data[i][3] =  new ServiceImpl();

        }

        return data;
    }

    public MyTableModel getTableModel() {
        return tableModel;
    }

    // se lanseaza o cerere de oferta
    public abstract void launchOffer();

    // se anuleaza o cerere de oferta
    public abstract void revokeOffer();

    //  se face o oferta
    public abstract void makeOffer();

    //oferta este acceptata/depasita/refuzata
    public abstract void acceptOffer();

    public abstract void surpassOffer();

    public abstract void refuseOffer();

    // transferul de servicii/produse
    public abstract void doTransfer();


}
