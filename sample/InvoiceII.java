package sample;

import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class InvoiceII {

    SimpleIntegerProperty buildingID;
    SimpleBooleanProperty billed;
    SimpleDoubleProperty totalPrice;
    SimpleIntegerProperty Bill_To;
    SimpleBooleanProperty Paid;
    SimpleStringProperty Date;

    public InvoiceII() {
    }

    public InvoiceII(int buildingID, boolean billed, Double totalPrice, int bill_To, boolean paid, String date) {
        this.buildingID = new SimpleIntegerProperty(buildingID);
        this.billed = new SimpleBooleanProperty(billed);
        this.totalPrice = new SimpleDoubleProperty(totalPrice);
        this.Bill_To = new SimpleIntegerProperty(bill_To);
        this.Paid = new SimpleBooleanProperty(paid);
        this.Date = new SimpleStringProperty(date);
    }

    public int getBuildingID() {
        return buildingID.get();
    }

    public void setBuildingID(int buildingID) {
        this.buildingID.set(buildingID);
    }

    public boolean isBilled() {
        return billed.get();
    }

    public void setBilled(boolean billed) {
        this.billed.set(billed);
    }

    public Double getTotalPrice() {
        return totalPrice.get();
    }

    public void setTotalPrice(Double totalPrice) {
        this.totalPrice.set(totalPrice);
    }

    public int getBill_To() {
        return Bill_To.get();
    }

    public void setBill_To(int bill_To) {
        this.Bill_To.set(bill_To);
    }

    public boolean isPaid() {
        return Paid.get();
    }

    public void setPaid(boolean paid) {
        Paid.set(paid);
    }

    public String getDate() {
        return Date.get();
    }

    public void setDate(String date) {
        Date.set(date);
    }
}
