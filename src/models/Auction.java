package models;

/**
 * Created with IntelliJ IDEA.
 * User: nogai
 * Date: 3/26/13
 * Time: 2:48 PM
 * To change this template use File | Settings | File Templates.
 */
public class Auction {

    private String user;
    private StatusTypes status;
    private float price;

    public Auction(String user) {
        this.user = user;
        this.price = -1;
        this.status = StatusTypes.No_Offer;

    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Auction auction = (Auction) o;

        if (!user.equals(auction.user)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return user.hashCode();
    }

    public Auction(String user, StatusTypes status, float price) {
        this.user = user;
        this.status = status;
        this.price = price;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public StatusTypes getStatus() {
        return status;
    }

    public void setStatus(StatusTypes status) {
        this.status = status;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    @Override
    public String toString() {
        String s = user + " " + status;
        if (price > 0 && !StatusTypes.No_Offer.equals(status))
            s += " " + price + "$";

        return s;
    }
}
