package state;

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
}
