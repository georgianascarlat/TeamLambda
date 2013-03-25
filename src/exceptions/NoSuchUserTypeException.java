package exceptions;

/**
 * Created with IntelliJ IDEA.
 * User: nogai
 * Date: 3/25/13
 * Time: 12:21 PM
 * To change this template use File | Settings | File Templates.
 */
public class NoSuchUserTypeException extends Exception {

    private String type;

    public NoSuchUserTypeException(String type) {
        this.type = type;
    }

    public String getType() {
        return type;
    }
}
