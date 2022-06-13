package by.incubator.application.rent;

import java.util.Date;

public class Rent {
    private int id;
    private Date date;
    private double price;

    Rent() {}
    Rent(Date date, double price) {
        this.date = date;
        this.price = price;
    }

    public Rent(int id, Date date, double price) {
        this(date, price);
        this.id = id;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String toString() {
        return id + " " + date + " " + price;
    }
}
