package app;

import exceptions.NoSuchUserException;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;

/**
 * Created with IntelliJ IDEA.
 * User: nogai
 * Date: 3/24/13
 * Time: 7:01 PM
 * To change this template use File | Settings | File Templates.
 */
public class WebServiceClientImpl implements WebServiceClient {

    private HashMap<String, String> user_config_file_assoc;

    public WebServiceClientImpl() {
        this.user_config_file_assoc = new HashMap<String, String>();
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
}
