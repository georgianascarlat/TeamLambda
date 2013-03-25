package models;

import exceptions.NoSuchUserTypeException;

/**
 * Created with IntelliJ IDEA.
 * User: nogai
 * Date: 3/25/13
 * Time: 12:13 PM
 * To change this template use File | Settings | File Templates.
 */
public enum UserType {

    BUYER("buyer"),
    SELLER("seller");


    private String text;

    UserType(String text) {
        this.text = text;
    }

    public String getText() {
        return this.text;
    }

    public static UserType fromString(String text) throws NoSuchUserTypeException {
        if (text != null) {
            for (UserType b : UserType.values()) {
                if (text.equalsIgnoreCase(b.text)) {
                    return b;
                }
            }
        }
        throw new NoSuchUserTypeException();
    }
}
