package worker;

import mediator.MediatorGUI;
import models.Auction;
import models.MyTableModel;
import models.Service;
import models.ServiceImpl;

import javax.swing.*;

import java.util.List;
import java.util.concurrent.ExecutionException;

import static models.MyTableModel.PROGRESS_BAR_COLUMN;
import static models.MyTableModel.USER_LIST_COLUMN;
import static models.StatusTypes.Transfer_Completed;
import static models.StatusTypes.Transfer_Failed;

/**
 * Created with IntelliJ IDEA.
 * User: nogai
 * Date: 3/29/13
 * Time: 5:11 PM
 * To change this template use File | Settings | File Templates.
 */
public class FileTransferWorker extends SwingWorker<Service, Service> {

    private MediatorGUI mediator;
    private String serviceName;
    private Auction auction;
    private JTable table;
    private int serviceRow;

    public FileTransferWorker(MediatorGUI mediator, String serviceName, Auction auction, JTable table, int serviceRow) {
        this.mediator = mediator;
        this.serviceName = serviceName;
        this.auction = auction;
        this.table = table;
        this.serviceRow = serviceRow;
    }

    @Override
    protected Service doInBackground() throws Exception {

        Service service;

        while (true) {
            service = mediator.serviceTransfer(serviceName, auction);


            if (Transfer_Failed.equals(service.getPurchaseStatus()) ||
                    Transfer_Completed.equals(service.getPurchaseStatus()))
                return service;
            if (isCancelled()) {
                service.setPurchaseStatus(Transfer_Failed);
                return service;
            }
            publish(service);
        }

    }

    @Override
    protected void process(List<Service> chunks) {
        int size = chunks.size();
        DefaultListModel listModel = (DefaultListModel) table.getModel().getValueAt(serviceRow, USER_LIST_COLUMN);

        Service service;
        if (size > 0) {
            service = chunks.get(size - 1);
            updateTable(listModel, service);
        }

    }


    @Override
    protected void done() {

        DefaultListModel listModel = (DefaultListModel) table.getModel().getValueAt(serviceRow, USER_LIST_COLUMN);

        try {
            Service service = get();
            updateTable(listModel, service);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
    }

    private void updateTable(DefaultListModel listModel, Service service) {

        if (listModel.removeElement(auction)) {
            table.getModel().setValueAt(service, serviceRow, PROGRESS_BAR_COLUMN);
            auction.setStatus(service.getPurchaseStatus());
            listModel.addElement(auction);
        } else {
            table.getModel().setValueAt(new ServiceImpl(), serviceRow, PROGRESS_BAR_COLUMN);
        }

        table.repaint();

    }
}
