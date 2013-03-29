package mock;

import models.*;
import app.WebServiceClient;
import exceptions.NoSuchUserException;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

/**
 * Created with IntelliJ IDEA.
 * User: nogai
 * Date: 3/24/13
 * Time: 7:01 PM
 * To change this template use File | Settings | File Templates.
 */
public class WebServiceClientImpl implements WebServiceClient {

    private HashMap<String, String> user_config_file_assoc;
    private List<Service> servicesInProgress;

    public WebServiceClientImpl() {
        this.user_config_file_assoc = new HashMap<String, String>();
        this.servicesInProgress = new LinkedList<Service>();
        this.user_config_file_assoc.put("ana", "config_files/ana_config.txt");
    }

    @Override
    public List<String> getServices(LoginInfo loginInfo) throws NoSuchUserException {

        List<String> services = new LinkedList<String>();
        String fileName = user_config_file_assoc.get(loginInfo.getUsername());
        Scanner scanner = null;

        if (fileName == null)
            throw new NoSuchUserException();

        try {

            scanner = new Scanner(new File(fileName));
            int n = scanner.nextInt();
            scanner.nextLine();

            for (int i = 0; i < n; i++) {
                services.add(scanner.nextLine());
            }


            return services;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            throw new NoSuchUserException();
        } finally {
            if (scanner != null)
                scanner.close();
        }

    }

    @Override
    public synchronized Service serviceTransfer(String username, String serviceName, Auction auction) {

        int progress;
        Service service;

        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        for (Iterator<Service> it = servicesInProgress.iterator(); it.hasNext(); ) {
            service = it.next();
            if (service.getName().equals(serviceName)) {

                progress = service.getPurchaseProgress();
                if (progress >= 100) {

                    service.setPurchaseStatus(StatusTypes.Transfer_Completed);
                    service.setPurchaseProgress(100);
                    it.remove();

                } else {
                    service.setPurchaseStatus(StatusTypes.Transfer_In_Progress);
                    service.setPurchaseProgress(progress + 1);
                }

                return service;
            }
        }

        service = new ServiceImpl(serviceName, StatusTypes.Transfer_Started, auction.getUser());
        servicesInProgress.add(service);
        return service;
    }
}
