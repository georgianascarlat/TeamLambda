package state;

import models.MyTableModel;

import javax.swing.*;

/**
 * Created with IntelliJ IDEA.
 * User: nogai
 * Date: 3/25/13
 * Time: 12:03 PM
 * To change this template use File | Settings | File Templates.
 */
public class StateManager {


    private SessionState sessionState;

    StateManager(SessionState sessionState) {
        this.sessionState = sessionState;
    }

    public static StateManager setStateManager(StateManager stateManager, SessionState state) {
        if (stateManager == null) {
            return new StateManager(state);
        } else {
            stateManager.setSessionState(state);
            return stateManager;
        }

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
}
