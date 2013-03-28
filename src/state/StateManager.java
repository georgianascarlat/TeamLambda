package state;

import models.Auction;
import models.PopupListener;

import javax.swing.*;
import javax.swing.table.TableModel;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: nogai
 * Date: 3/25/13
 * Time: 12:03 PM
 * To change this template use File | Settings | File Templates.
 */
public class StateManager {


    private SessionState sessionState;

    public StateManager(SessionState sessionState) {
        this.sessionState = sessionState;
    }

    public StateManager() {

        this.sessionState = new SessionState() {
            @Override
            protected PopupListener getTableMouseListener() {
                return null;
            }

            @Override
            protected PopupListener getListMouseListener() {
                return null;
            }

            @Override
            public void launchOffer(int row) {

            }

            @Override
            public void dropOffer(int row) {

            }

            @Override
            protected boolean canAddUser(TableModel model, int row) {
                return false;
            }

            @Override
            public void login() {

            }


            @Override
            public void auctionStatusChanged(String service, Auction auction) {

            }


            @Override
            protected void verifyStatus(int row) {

            }

            @Override
            public void addUser(String username, String type, List<String> services) {

            }

            @Override
            public void removeUser(String name, String type) {

            }


        };
    }


    public SessionState getSessionState() {
        return sessionState;
    }

    public void setSessionState(SessionState sessionState) {
        this.sessionState = sessionState;
    }

    // se lanseaza o cerere de oferta
    public void launchOffer(int row) {
        sessionState.launchOffer(row);
    }


    public JTable getTable() {
        return sessionState.getTable();
    }

    public void dropOffer(int row) {
        sessionState.dropOffer(row);
    }

    public void addUser(String username, String type, List<String> services) {
        sessionState.addUser(username, type, services);
    }

    public void removeUser(String name, String type) {
        sessionState.removeUser(name, type);
    }

    public void login() {
        sessionState.login();
    }


    public void auctionStatusChanged(String service, Auction auction) {
        sessionState.auctionStatusChanged(service,auction);
    }


}
