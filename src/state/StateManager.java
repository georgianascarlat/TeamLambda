package state;

import models.MyTableModel;

import javax.swing.*;
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

    public void newUserAppeared(String username, String type, List<String> services) {
        sessionState.newUserAppeared(username, type, services);
    }

    public void removeUser(String name, String type) {
        sessionState.removeUser(name, type);
    }
}
